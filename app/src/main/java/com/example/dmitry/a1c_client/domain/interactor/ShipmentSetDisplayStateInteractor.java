package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import javax.inject.Inject;

/**
 * Created by Admin on 27.12.2016.
 */

public class ShipmentSetDisplayStateInteractor extends SetDisplayStateInteractor {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;

    @Inject
    public ShipmentSetDisplayStateInteractor() {}

    @Override
    protected void operation() {
        stateKeeper.change(state1 -> state1.toBuilder().whatToShow(state).build());
    }
}
