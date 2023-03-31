package com.cain.androidpro;

import static java.lang.reflect.Proxy.newProxyInstance;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.IInterface;
import android.util.Log;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 网络状态hook 工具类<br/>
 * Created by wentaoli on 2017/8/4.
 */
public class Hook {
    private static final String TAG = "Hook";
    /**
     * HookNetworkInfo
     */
    public static NetworkInfo networkInfo = buildNetworkInfo(ConnectivityManager.TYPE_MOBILE,
            ConnectivityManager.TYPE_MOBILE, "MOBILE", "3G-net", "uninet");

    /**
     * 动态注册的广播接收器（接收网络变化）名称列表
     */
    private static Set<String> dynamicReceiverList = new HashSet<>();

    /**
     * 构建 networkInfo
     *
     * @param type        type
     * @param subType     subType
     * @param typeName    type name
     * @param subTypeName subType name
     * @param extra       extra
     * @return networkInfo
     */
    public static NetworkInfo buildNetworkInfo(int type, int subType, String typeName, String subTypeName, String extra) {
        NetworkInfo info = null;
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
                NetworkInfo networkInfo1 = new NetworkInfo(type,subType,typeName,subTypeName);
                networkInfo1.setDetailedState(NetworkInfo.DetailedState.CONNECTED, "connected", extra);
            }

            Constructor<NetworkInfo> constructor = NetworkInfo.class.getConstructor(int.class, int.class, String.class, String.class);
            info = constructor.newInstance(type, subType, typeName, subTypeName);

            Method detail = NetworkInfo.class.getDeclaredMethod("setDetailedState", NetworkInfo.DetailedState.class, String.class, String.class);
            detail.setAccessible(true);
            detail.invoke(info, NetworkInfo.DetailedState.CONNECTED, "connected", extra);

            Method isAvailable = NetworkInfo.class.getDeclaredMethod("setIsAvailable", boolean.class);
            isAvailable.setAccessible(true);
            isAvailable.invoke(info, true);
        } catch (Exception e) {
            Log.e(TAG, "buildNetworkInfo# error msg = %s" + e.getMessage());
        }
        return info;
    }

    private static class BinderHookHandler implements InvocationHandler {
        Context context;

        // 原始的IConnectivityManager对象 (也是IInterface)
        Object base;

        BinderHookHandler(Context context, Object base) {
            this.context = context.getApplicationContext();
            this.base = base;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getActiveNetworkInfo".equals(method.getName())) {
                if (SettingsPreferences.getHookNetwork(context)) {
                    return networkInfo;
                }
            }

            return method.invoke(base, args);
        }
    }

    /**
     * hook 网络状态，使可以用wifi模拟3g网络
     *
     * @param context context
     */
    public static void hookNetwork(final Context context) {
        try {
            // 首先获取ServiceManager class
            Class<?> serviceManager = Class.forName("android.os.ServiceManager");

            Method getService = serviceManager.getDeclaredMethod("getService", String.class);
            // 调用首先获取ServiceManager的"getService"方法获取原始的 IBinder 对象
            final IBinder baseBinder = (IBinder) getService.invoke(null, Context.CONNECTIVITY_SERVICE);

            // 使用动态代理伪造一个新的 IBinder对象
            IBinder hookedBinder = (IBinder) newProxyInstance(serviceManager.getClassLoader(),
                    new Class<?>[]{IBinder.class}, new InvocationHandler() {
                        private Object mIConnectivityManager = null;

                        /**
                         * 获取原本的IConnectivityManager对象
                         *
                         * @param binder binder
                         * @return IConnectivityManager 对象
                         */
                        private Object getBaseManager(@NonNull IBinder binder, Class<?> stubCls) {
                            try {
                                Method asInterfaceMethod = stubCls.getDeclaredMethod("asInterface", IBinder.class);
                                return asInterfaceMethod.invoke(null, binder);
                            } catch (Exception e) {
                                Log.e(TAG, "getBaseManager# error msg = " + e.getMessage());
                            }
                            return null;
                        }

                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            if (!"queryLocalInterface".equals(method.getName())) {
                                return method.invoke(baseBinder, args);
                            }
                            if (mIConnectivityManager != null) {
                                return mIConnectivityManager;
                            }

                            // 替换掉 queryLocalInterface 方法的返回值
                            Object base = getBaseManager(baseBinder, Class.forName("android.net.IConnectivityManager$Stub"));
                            mIConnectivityManager = newProxyInstance(proxy.getClass().getClassLoader(),
                                    // asInterface 的时候会检测是否是特定类型的接口然后进行强制转换, 因此这里的动态代理生成的类型信息的类型必须是正确的
                                    new Class[]{IInterface.class, Class.forName("android.net.IConnectivityManager")},
                                    new BinderHookHandler(context, base));
                            return mIConnectivityManager;
                        }
                    });
            // 把伪造的 IBinder 对象放进ServiceManager的cache里面
            Field cacheField = serviceManager.getDeclaredField("sCache");
            cacheField.setAccessible(true);
            @SuppressWarnings("unchecked")
            Map<String, IBinder> cache = (Map<String, IBinder>) cacheField.get(null);
            cache.put(Context.CONNECTIVITY_SERVICE, hookedBinder);
        } catch (Exception e) {
            Log.e(TAG, "hookNetwork# error msg = " + e.getMessage());
        }
    }

    /**
     * hook 掉 ActivityManagerService， 目的：使app可以发送网络变化通知。
     *
     * @param context context
     * @return 广播
     */
    public static List<BroadcastReceiver> hookActivityManagerService(Context context) {
        try {
            Class<?> activityManagerNativeClass = Class.forName("android.app.ActivityManagerNative");

            // 获取 gDefault 这个字段, 想办法替换它
            Field gDefaultField = activityManagerNativeClass.getDeclaredField("gDefault");
            gDefaultField.setAccessible(true);
            final Object gDefault = gDefaultField.get(null);

            // 4.x以上的gDefault是一个 android.util.Singleton对象; 我们取出这个单例里面的字段
            Class<?> singleton = Class.forName("android.util.Singleton");
            final Field mInstanceField = singleton.getDeclaredField("mInstance");
            mInstanceField.setAccessible(true);

            // ActivityManagerNative 的gDefault对象里面原始的 IActivityManager对象
            final Object rawIActivityManager = mInstanceField.get(gDefault);

            // 创建一个这个对象的代理对象, 然后替换这个字段, 让我们的代理对象帮忙干活
            Class<?> iActivityManagerInterface = Class.forName("android.app.IActivityManager");
            final Object proxy = newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class<?>[]{iActivityManagerInterface}, new InvocationHandler() {
                        @Override
                        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                            // hook 广播注册
                            if ("registerReceiver".equals(method.getName())) {
                                if (args != null && args.length > 3 && args[3] instanceof IntentFilter) {
                                    IntentFilter filter = (IntentFilter) args[3];
                                    String action = filter.countActions() > 0 ? filter.getAction(0) : "";
                                    if (ConnectivityManager.CONNECTIVITY_ACTION.equals(action)) {
                                        String receiverName = getReceiverName(args[2]);
                                        if (receiverName != null) {
                                            dynamicReceiverList.add(receiverName);
                                        }
                                    }
                                }
                            }
                            return method.invoke(rawIActivityManager, args);
                        }
                    });
            mInstanceField.set(gDefault, proxy);
        } catch (Exception e) {
            Log.e(TAG, "hookActivityManagerService# error msg = " + e.getMessage());
        }
        return null;
    }

    /**
     * 通过InnerReceiver获取广播名称
     *
     * @param innerReceiver innerReceiver
     * @return name
     */
    private static String getReceiverName(Object innerReceiver) {
        Object receiverDispatcher = null;
        try {
            Field f = innerReceiver.getClass().getDeclaredField("mStrongRef");
            f.setAccessible(true);
            receiverDispatcher = f.get(innerReceiver);
        } catch (Exception e) {
            Log.e(TAG, "getReceiverName# error msg = " + e.getMessage());
        }
        try {
            if (receiverDispatcher == null) {
                Field f = innerReceiver.getClass().getDeclaredField("mDispatcher");
                f.setAccessible(true);
                Object weakRef = f.get(innerReceiver);
                if (weakRef instanceof WeakReference) {
                    receiverDispatcher = ((WeakReference) weakRef).get();
                }
                if (receiverDispatcher == null) {
                    return null;
                }
            }
            Field receiverField = receiverDispatcher.getClass().getDeclaredField("mReceiver");
            receiverField.setAccessible(true);
            return receiverField.get(receiverDispatcher).getClass().getName();
        } catch (Exception e) {
            Log.e(TAG, "getReceiverName# error msg = " + e.getMessage());
        }
        return null;
    }

    /**
     * 改变网络hook状态
     *
     * @param context context
     * @return 是否hook
     */
    @SuppressWarnings("deprecation")
    public static boolean changeNetworkHookStatus(Context context) {
        boolean oldStatus = SettingsPreferences.getHookNetwork(context);
        NetworkInfo oldInfo = NetworkUtils.getConnectedNetworkInfo(context);
        SettingsPreferences.setHookNetwork(context, !oldStatus);
        // test
        NetworkInfo newInfo = NetworkUtils.getConnectedNetworkInfo(context);
        // 网络发生变化，发送广播
        if (oldInfo != null && newInfo != null && newInfo.getType() != oldInfo.getType()) {
            Intent intent = new Intent(ConnectivityManager.CONNECTIVITY_ACTION + ".hook");
            intent.putExtra(ConnectivityManager.EXTRA_NETWORK_INFO, newInfo);
            intent.putExtra("networkType", newInfo.getType());
            intent.putExtra("test", "network-test");
            context.sendBroadcast(intent);
            Hook.hookNetworkChangedBroadcastReceiver(context, intent);
        }
        return !oldStatus;
    }

    /**
     * hook网络改变广播接收器
     *
     * @param context context
     * @param intent  intent
     */
    private static void hookNetworkChangedBroadcastReceiver(Context context, Intent intent) {
        hookNetworkChangedStaticBroadcastReceiver(context, intent);
        hookNetworkChangedDynamicBroadcastReceiver(context, intent);
    }

    /**
     * hook 静态注册的网络改变广播接收器
     *
     * @param context context
     * @param intent  intent
     */
    private static void hookNetworkChangedStaticBroadcastReceiver(Context context, Intent intent) {
        Intent i = new Intent(ConnectivityManager.CONNECTIVITY_ACTION);
        i.setPackage(context.getPackageName());
        List<ResolveInfo> list = context.getPackageManager().queryBroadcastReceivers(i, PackageManager.MATCH_ALL);
        if (list != null && list.size() > 0) {
            Intent out = new Intent(intent);
            out.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
            for (ResolveInfo resolveInfo : list) {
                try {
                    Class<?> clazz = Class.forName(resolveInfo.activityInfo.name);
                    BroadcastReceiver receiver = (BroadcastReceiver) clazz.newInstance();
                    receiver.onReceive(context, out);
                } catch (Exception e) {
                    Log.e(TAG, "hookNetworkChangedStaticBroadcastReceiver# error msg " + e.getMessage());
                }
            }
        }
    }

    /**
     * hook 动态注册的网络改变广播接收器
     *
     * @param context context
     * @param intent  intent
     */
    private static void hookNetworkChangedDynamicBroadcastReceiver(Context context, Intent intent) {
        try {
            // 先获取到当前的ActivityThread对象
            Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
            Method currentActivityThreadMethod = activityThreadClass.getDeclaredMethod("currentActivityThread");
            currentActivityThreadMethod.setAccessible(true);
            Object currentActivityThread = currentActivityThreadMethod.invoke(null);
            Field f = activityThreadClass.getDeclaredField("mPackages");
            f.setAccessible(true);
            Map map = (Map) f.get(currentActivityThread);
            Object loadedApkRef = map.get(context.getPackageName());
            if (loadedApkRef instanceof WeakReference) {
                Object loadedApk = ((WeakReference) loadedApkRef).get();
                Field field = loadedApk.getClass().getDeclaredField("mReceivers");
                field.setAccessible(true);
                Map mReceiversMap = (Map) field.get(loadedApk);
                for (Object key : mReceiversMap.keySet()) {
                    Map items = (Map) mReceiversMap.get(key);
                    if (items == null || items.isEmpty()) {
                        continue;
                    }
                    for (Object o : items.keySet()) {
                        if (o instanceof BroadcastReceiver && dynamicReceiverList.contains(o.getClass().getName())) {
                            Intent out = new Intent(intent);
                            out.setAction(ConnectivityManager.CONNECTIVITY_ACTION);
                            ((BroadcastReceiver) o).onReceive(context, out);
                        }
                    }
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "hookNetworkChangedDynamicBroadcastReceiver# error msg = " + e.getMessage());
        }
    }

}
