package com.example.dmitry.a1c_client.android;

import android.app.Application;

import com.example.dmitry.a1c_client.di.DaggerIncomeTaskComponent;
import com.example.dmitry.a1c_client.di.DaggerMainComponent;
import com.example.dmitry.a1c_client.di.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.IncomeTaskModule;
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
    public IncomeTaskComponent getIncomeTaskComponent(){
        return incomeTaskComponent;}


    private void initDI() {
        mainComponent = DaggerMainComponent.create();
    }

    public void clearIncomeTaskComponent(){
        incomeTaskComponent = null;
    }
    public IncomeTaskComponent buildIncomeTaskComponent(IncomeTaskView view){
        incomeTaskComponent = DaggerIncomeTaskComponent.builder()
                .incomeTaskModule(new IncomeTaskModule(view)).build();
        return incomeTaskComponent;
    }
}
