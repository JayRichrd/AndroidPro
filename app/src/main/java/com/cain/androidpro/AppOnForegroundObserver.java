package com.cain.androidpro;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.OnLifecycleEvent;

public interface AppOnForegroundObserver extends LifecycleObserver {
    /**
     * 应用进入前台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    void onAppForeground();

    /**
     * 应用进入后台
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    void onAppBackground();

}
