package com.cain.androidpro;

import android.app.Application;

import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(true)  // (Optional) Whether to show thread info or not. Default true
                .tag("androidpro")   // (Optional) Global tag for every log. Default PRETTY_LOGGER
                .methodCount(1)
                .build();
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
    }
}
