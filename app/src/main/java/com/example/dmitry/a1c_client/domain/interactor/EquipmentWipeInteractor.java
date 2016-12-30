package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;

import javax.inject.Inject;

/**
 * Created by Admin on 29.12.2016.
 */

public class EquipmentWipeInteractor extends Interactor {
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;

    @Inject
    public EquipmentWipeInteractor() {}

    @Override
    protected void operation() {
    }
}
