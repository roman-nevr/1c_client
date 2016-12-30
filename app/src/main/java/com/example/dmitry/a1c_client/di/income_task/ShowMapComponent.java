package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.android.views.fragments.ShowMapDialogFragment;
import com.example.dmitry.a1c_client.di.scopes.MapScope;

import dagger.Component;

/**
 * Created by Admin on 29.12.2016.
 */

@Component(dependencies = IncomeTaskComponent.class)
@MapScope
public interface ShowMapComponent {
    void inject(ShowMapDialogFragment fragment);
}
