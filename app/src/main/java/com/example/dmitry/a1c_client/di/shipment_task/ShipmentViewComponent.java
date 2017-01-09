package com.example.dmitry.a1c_client.di.shipment_task;

import com.example.dmitry.a1c_client.android.views.shipment.ShipmentTaskActivity;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by Admin on 09.01.2017.
 */
@Component(dependencies = ShipmentTaskComponent.class, modules = ShipmentViewModule.class)
@PerActivity
public interface ShipmentViewComponent {
    void inject(ShipmentTaskActivity activity);
}
