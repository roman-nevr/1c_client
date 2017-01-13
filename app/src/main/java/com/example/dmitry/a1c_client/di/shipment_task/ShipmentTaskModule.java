package com.example.dmitry.a1c_client.di.shipment_task;

import com.example.dmitry.a1c_client.data.dummy.ShipmentTaskRepositoryImpl;
import com.example.dmitry.a1c_client.di.scopes.TaskScope;
import com.example.dmitry.a1c_client.domain.ShipmentTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 23.12.2016.
 */

@Module
public class ShipmentTaskModule {

    @Provides
    @TaskScope
    public StateKeeper<ShipmentTaskState> provideShipmentTaskState(){
        return new StateKeeper<>(ShipmentTaskState.EMPTY);
    }

    @Provides
    @TaskScope
    public ShipmentTaskRepository provideShipmentTaskRepository(){
        return new ShipmentTaskRepositoryImpl();
    }
}
