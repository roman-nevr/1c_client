package com.example.dmitry.a1c_client.di.equipment;

import com.example.dmitry.a1c_client.di.scopes.TaskScope;
import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Admin on 26.12.2016.
 */
@Component(modules = EquipmentTaskModule.class)
@Singleton
public interface EquipmentTaskComponent {
    StateKeeper<EquipmentTaskState> provideEquipmentTaskComponent();

    EquipmentRepository provideEquipmentRepository();
}
