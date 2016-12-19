package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.data.IncomeTaskRepositoryImpl;
import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roma on 18.12.2016.
 */
@Module
public class IncomeTaskModule {
    private IncomeTaskView incomeTaskView;

    public IncomeTaskModule(IncomeTaskView incomeTaskView) {
        this.incomeTaskView = incomeTaskView;
    }

    @Provides
    @Singleton
    public StateKeeper<IncomeTaskState> provideIncomeTaskState(){
        return new StateKeeper<>(IncomeTaskState.EMPTY);
    }

    @Provides
    @Singleton
    public IncomeTaskRepository provideIncomeTaskRepository(){
        return new IncomeTaskRepositoryImpl();
    }

    @Provides
    @Singleton
    public IncomeTaskView provideIncomeTaskView(){
        return incomeTaskView;
    }
}
