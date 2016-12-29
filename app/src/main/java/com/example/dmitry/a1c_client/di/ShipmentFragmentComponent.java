package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.ShipmentListFragment;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

@Component(dependencies = MainComponent.class,modules = ShipmentListModule.class)
@PerActivity
public interface ShipmentFragmentComponent {
    void inject(ShipmentListFragment fragment);
}
