package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import dagger.Module;
import dagger.Provides;

/**
 * Created by roma on 18.12.2016.
 */
@Module
public class IncomeTaskModule {
    @Provides
    @PerActivity
    public StateKeeper<IncomeTaskState> provideIncomeTaskState(){
        return new StateKeeper<>(IncomeTaskState.EMPTY);
    }
}
