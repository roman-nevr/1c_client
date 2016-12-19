package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.android.IncomeTaskActivity;
import com.example.dmitry.a1c_client.domain.interactor.GetNomenclatureByBarCodeInteractor;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Admin on 19.12.2016.
 */

@Component(modules = IncomeTaskModule.class)
@Singleton
public interface IncomeTaskComponent {
    void inject(IncomeTaskActivity fragment);
}
