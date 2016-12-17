package com.example.dmitry.a1c_client.di;

import android.support.v7.view.menu.MenuView;

import com.example.dmitry.a1c_client.presentation.IncomeView;

import dagger.Module;
import dagger.Provides;

@Module
public class IncomeModule {
    private IncomeView incomeView;

    public IncomeModule(IncomeView incomeView) {
        this.incomeView = incomeView;
    }

    @Provides
    @PerActivity
    public IncomeView provideIncomeView() {
        return incomeView;
    }
}
