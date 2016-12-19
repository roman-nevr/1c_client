package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.IncomeListFragment;

import dagger.Component;

@Component(dependencies = MainComponent.class,modules = IncomeListModule.class)
@PerActivity
public interface IncomeFragmentComponent {
    void inject(IncomeListFragment incomeFragment);
}
