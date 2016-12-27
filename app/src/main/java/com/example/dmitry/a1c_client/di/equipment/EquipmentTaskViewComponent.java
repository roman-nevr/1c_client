package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.android.equipment.EquipmentTaskFragment;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by Admin on 26.12.2016.
 */

@Component(dependencies = EquipmentTaskComponent.class)
@PerActivity
public interface EquipmentTaskViewComponent {
    void inject(EquipmentTaskFragment fragment);
}
