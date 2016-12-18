package com.example.dmitry.a1c_client.di;

import com.example.dmitry.a1c_client.data.DocumentRepositoryImpl;
import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeListState;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class MainModule {
    @Provides
    @Singleton
    public DocumentRepository provideDocumentRepository() {
        return new DocumentRepositoryImpl();
    }

    @Provides
    @Singleton
    public StateKeeper<IncomeListState> provideIncomeListState() {
        return new StateKeeper<>(IncomeListState.EMPTY);
    }
}
