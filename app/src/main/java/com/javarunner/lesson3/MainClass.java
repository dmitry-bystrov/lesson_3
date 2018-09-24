package com.javarunner.lesson3;

import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class MainClass extends Application {
    private static MainClass instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        Timber.plant(new Timber.DebugTree());
    }

    public static Context getContext() {
        return instance.getApplicationContext();
    }
}
