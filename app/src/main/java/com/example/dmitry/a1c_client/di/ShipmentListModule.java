package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListView;

import dagger.Module;
import dagger.Provides;

@Module
public class ShipmentListModule {
    private ShipmentListView shipmentView;

    public ShipmentListModule(ShipmentListView incomeView) {
        this.shipmentView = incomeView;
    }

    @Provides
    @PerActivity
    public ShipmentListView provideIncomeView() {
        return shipmentView;
    }
}
