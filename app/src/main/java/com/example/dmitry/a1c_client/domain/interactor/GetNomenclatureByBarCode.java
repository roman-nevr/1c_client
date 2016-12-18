package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.data.IncomeTaskRepositoryImpl;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import javax.inject.Inject;

/**
 * Created by roma on 18.12.2016.
 */

public class GetNomenclatureByBarCode extends Interactor {
    @Inject StateKeeper<IncomeTaskState> incomeTaskStateStateKeeper;
    @Inject
    IncomeTaskRepositoryImpl taskRepository;

    @Override
    protected void operation() {

    }
}
