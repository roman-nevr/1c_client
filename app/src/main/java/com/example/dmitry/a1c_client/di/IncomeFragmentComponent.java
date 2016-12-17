package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.IncomeFragment;

import dagger.Component;

@Component(dependencies = MainComponent.class,modules = IncomeModule.class)
@PerActivity
public interface IncomeFragmentComponent {
    void inject(IncomeFragment incomeFragment);
}
