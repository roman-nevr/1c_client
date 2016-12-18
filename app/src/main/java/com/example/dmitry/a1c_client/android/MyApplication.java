package com.example.dmitry.a1c_client.android;

import android.app.Application;

import com.example.dmitry.a1c_client.di.DaggerMainComponent;
import com.example.dmitry.a1c_client.di.MainComponent;


public class MyApplication extends Application {
    private MainComponent mainComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDI();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    private void initDI() {
        mainComponent = DaggerMainComponent.create();
    }
}
