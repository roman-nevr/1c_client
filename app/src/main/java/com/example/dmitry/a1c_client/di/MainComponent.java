package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipListState;
import com.example.dmitry.a1c_client.domain.entity.IncomeListState;
import com.example.dmitry.a1c_client.domain.entity.ShipmentListState;

import javax.inject.Singleton;

import dagger.Component;

@Component(modules = {MainModule.class})
@Singleton
public interface MainComponent {
    DocumentRepository provideDocumentRepository();

    StateKeeper<IncomeListState> provideIncomeState();

    StateKeeper<ShipmentListState> provideShipmentState();

    StateKeeper<EquipListState> provideEquipState();
}
