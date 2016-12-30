package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.data.EquipmentRepositoryImpl;
import com.example.dmitry.a1c_client.di.scopes.TaskScope;
import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 26.12.2016.
 */

@Module
public class EquipmentTaskModule {
    @Provides
    @Singleton
    public StateKeeper<EquipmentTaskState> provideEquipmentTaskState(){
        return new StateKeeper<>(EquipmentTaskState.EMPTY);
    }

    @Provides
    @Singleton
    public EquipmentRepository provideEquipmentRepository(){
        return new EquipmentRepositoryImpl();
    }
}
