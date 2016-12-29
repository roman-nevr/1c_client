package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.presentation.EquipmentView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 29.12.2016.
 */

@Module
public class EquipViewModule {
    private EquipmentView view;

    public EquipViewModule(EquipmentView view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    EquipmentView provideEquipmentView(){
        return view;
    }
}
