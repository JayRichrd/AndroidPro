package com.cain.androidpro;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.util.Log;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ConnectivityManagerProxy {

    private static final String TAG = "ConnectivityManagerProxy";

    public static void hook(Context context) {
        try {
            // 获取系统的ConnectivityManager对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            // 获取ConnectivityManager的mService字段，它是一个IBinder对象
            Class<?> connectivityManagerClass = Class.forName(connectivityManager.getClass().getName());
            Field serviceField = connectivityManagerClass.getDeclaredField("mService");
            serviceField.setAccessible(true);
            IBinder service = (IBinder) serviceField.get(connectivityManager);

            // 使用动态代理拦截IConnectivityManager的方法调用
            Class<?> iConnectivityManagerClass = Class.forName("android.net.IConnectivityManager");
            Object iConnectivityManager = Proxy.newProxyInstance(context.getClassLoader(), new Class[]{iConnectivityManagerClass}, new IConnectivityManagerInvocationHandler(service));

            // 将代理对象设置回ConnectivityManager的mService字段中
            serviceField.set(connectivityManager, iConnectivityManager);
        } catch (Exception e) {
            Log.e(TAG, "hook failed", e);
        }
    }

    private static class IConnectivityManagerInvocationHandler implements InvocationHandler {

        private IBinder mService;

        public IConnectivityManagerInvocationHandler(IBinder service) {
            mService = service;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            // 在这里拦截IConnectivityManager的方法调用，实现自定义的网络管理逻辑
            Log.d(TAG, "method: " + method.getName());
            return method.invoke(mService, args);
        }
    }
}
