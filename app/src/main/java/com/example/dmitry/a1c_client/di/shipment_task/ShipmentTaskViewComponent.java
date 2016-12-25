package com.example.dmitry.a1c_client.di.shipment_task;

import com.example.dmitry.a1c_client.android.ShipmentTaskFragment;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by roma on 25.12.2016.
 */

@Component(dependencies = ShipmentTaskComponent.class)
@PerActivity
public interface ShipmentTaskViewComponent {
    public void inject(ShipmentTaskFragment fragment);
}
