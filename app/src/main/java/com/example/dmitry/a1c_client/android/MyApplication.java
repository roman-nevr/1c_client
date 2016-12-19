package com.example.dmitry.a1c_client.android;

import android.app.Application;

import com.example.dmitry.a1c_client.di.DaggerMainComponent;
import com.example.dmitry.a1c_client.di.income_task.DaggerIncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;


public class MyApplication extends Application {
    private MainComponent mainComponent;
    private IncomeTaskComponent incomeTaskComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        initDI();
    }

    public MainComponent getMainComponent() {
        return mainComponent;
    }

    public IncomeTaskComponent getIncomeTaskComponent() {
        if (incomeTaskComponent == null) {
            incomeTaskComponent = DaggerIncomeTaskComponent.create();
        }
        return incomeTaskComponent;
    }


    private void initDI() {
        mainComponent = DaggerMainComponent.create();
    }

    public void clearIncomeTaskComponent(){
        incomeTaskComponent = null;
    }
}
