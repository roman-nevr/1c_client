package com.example.dmitry.a1c_client.di.shipment_task;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.di.scopes.TaskScope;
import com.example.dmitry.a1c_client.domain.ShipmentTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import dagger.Component;

/**
 * Created by Admin on 23.12.2016.
 */
@Component(modules = ShipmentTaskModule.class)
@TaskScope
public interface ShipmentTaskComponent {
    StateKeeper<ShipmentTaskState> provideShipmentTaskState();
    ShipmentTaskRepository provideShipmentTaskRepository();
}
