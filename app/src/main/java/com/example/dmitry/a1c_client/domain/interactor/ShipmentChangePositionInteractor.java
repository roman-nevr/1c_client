package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Admin on 27.12.2016.
 */

public class ShipmentChangePositionInteractor extends ChangePositionInteractor {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;

    @Inject
    public ShipmentChangePositionInteractor() {}

    @Override
    protected Shipable getValue() {
        return stateKeeper.getValue();
    }

    protected void updateState(List<ShipmentTaskPosition> positions) {
        stateKeeper.change(state -> state.toBuilder()
                .positions(positions)
                .build());
    }
}
