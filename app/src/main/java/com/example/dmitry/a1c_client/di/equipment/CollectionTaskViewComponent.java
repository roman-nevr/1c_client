package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.android.equipment.CollectionTaskFragment;
import com.example.dmitry.a1c_client.di.scopes.PerInstance;
import com.example.dmitry.a1c_client.di.scopes.ShipmentScope;

import dagger.Component;

/**
 * Created by Admin on 27.12.2016.
 */
@Component(dependencies = EquipmentTaskComponent.class)
@ShipmentScope
public interface CollectionTaskViewComponent {
    void inject(CollectionTaskFragment fragment);
}
