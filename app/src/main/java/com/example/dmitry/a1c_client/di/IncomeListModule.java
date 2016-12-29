package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.di.scopes.PerActivity;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListView;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 29.12.2016.
 */

@Module
public class IncomeListModule {
    private IncomeListView view;

    public IncomeListModule(IncomeListView view) {
        this.view = view;
    }

    @Provides
    @PerActivity
    public IncomeListView provideIncomeView() {
        return view;
    }
}
