package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.android.fragments.NewBarCodeDialogFragment;
import com.example.dmitry.a1c_client.di.scopes.DialogScope;

import dagger.Component;

/**
 * Created by Admin on 22.12.2016.
 */
@Component(dependencies = IncomeTaskComponent.class, modules = NewBarCodeViewModule.class)
@DialogScope
public interface NewBarCodeViewComponent {
    void inject(NewBarCodeDialogFragment fragment);
}
