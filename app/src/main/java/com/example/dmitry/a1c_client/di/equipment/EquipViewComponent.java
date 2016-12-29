package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.android.equipment.EquipmentTaskActivity;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by Admin on 29.12.2016.
 */
@Component(dependencies = EquipmentTaskComponent.class, modules = EquipViewModule.class)
@PerActivity
public interface EquipViewComponent {
    void inject(EquipmentTaskActivity activity);
}
