package com.example.dmitry.a1c_client.di.shipment_task;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.presentation.WindowView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 09.01.2017.
 */

@Module
public class ShipmentViewModule {
    private WindowView view;

    public ShipmentViewModule(WindowView view) {
        this.view = view;
    }

    @PerActivity
    @Provides
    WindowView provideShipmentView(){
        return view;
    }
}
