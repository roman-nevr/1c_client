package com.example.dmitry.a1c_client.di.income_task;

import com.example.dmitry.a1c_client.di.scopes.DialogScope;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 22.12.2016.
 */

@Module
public class NewBarCodeViewModule {
    private NewBarCodeDialogView view;

    public NewBarCodeViewModule(NewBarCodeDialogView view) {
        this.view = view;
    }

    @Provides
    @DialogScope
    public NewBarCodeDialogView provideNewBarCodeDialogView(){
        return view;
    }
}
