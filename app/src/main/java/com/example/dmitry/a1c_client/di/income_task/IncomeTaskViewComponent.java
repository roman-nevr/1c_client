package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.android.income_task.IncomeTaskActivity;
import com.example.dmitry.a1c_client.di.scopes.PerInstance;

import dagger.Component;

/**
 * Created by roma on 19.12.2016.
 */
@Component(dependencies = IncomeTaskComponent.class, modules = IncomeTaskViewModule.class)
@PerInstance
public interface IncomeTaskViewComponent {
    void inject(IncomeTaskActivity activity);
}
