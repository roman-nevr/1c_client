package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import dagger.Component;

/**
 * Created by Admin on 19.12.2016.
 */

@Component(modules = IncomeTaskModule.class)
@PerActivity
public interface IncomeTaskComponent {
    //void inject(IncomeTaskActivity fragment);
    StateKeeper<IncomeTaskState> provideIncomeTaskState();
    IncomeTaskRepository provideIncomeTaskRepository();
}
