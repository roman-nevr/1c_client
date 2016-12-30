package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.views.EquipListFragment;
import com.example.dmitry.a1c_client.di.scopes.PerActivity;

import dagger.Component;

/**
 * Created by Admin on 29.12.2016.
 */

@Component(dependencies = MainComponent.class,modules = EquipListModule.class)
@PerActivity
public interface EquipFragmentComponent {
    void inject(EquipListFragment fragment);
}
