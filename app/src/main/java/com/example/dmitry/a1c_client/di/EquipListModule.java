package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.presentation.document_list.EquipListView;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 29.12.2016.
 */

@Module
public class EquipListModule {
    private EquipListView view;

    public EquipListModule(EquipListView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    public EquipListView provideEquipView() {
        return view;
    }
}
