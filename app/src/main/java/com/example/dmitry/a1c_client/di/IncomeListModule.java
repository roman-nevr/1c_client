package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.presentation.IncomeListView;

import dagger.Module;
import dagger.Provides;

@Module
public class IncomeListModule {
    private IncomeListView incomeView;

    public IncomeListModule(IncomeListView incomeView) {
        this.incomeView = incomeView;
    }

    @Provides
    @PerActivity
    public IncomeListView provideIncomeView() {
        return incomeView;
    }
}
