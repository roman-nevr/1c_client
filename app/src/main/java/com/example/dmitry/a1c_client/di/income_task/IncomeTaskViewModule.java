package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.di.scopes.PerInstance;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roma on 19.12.2016.
 */
@Module
public class IncomeTaskViewModule {
    private IncomeTaskView incomeTaskView;

    public IncomeTaskViewModule(IncomeTaskView incomeTaskView) {
        this.incomeTaskView = incomeTaskView;
    }

    @Provides
    @PerInstance
    public IncomeTaskView provideIncomeTaskView(){
        return incomeTaskView;
    }
}
