package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.views.IncomeListFragment;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by Admin on 29.12.2016.
 */

@Component(dependencies = MainComponent.class,modules = IncomeListModule.class)
@PerActivity
public interface IncomeFragmentComponent {
    void inject(IncomeListFragment fragment);
}
