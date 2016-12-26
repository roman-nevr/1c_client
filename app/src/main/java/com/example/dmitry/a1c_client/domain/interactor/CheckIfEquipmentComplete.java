package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import javax.inject.Inject;

/**
 * Created by Admin on 26.12.2016.
 */

public class CheckIfEquipmentComplete extends Interactor {
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;

    @Inject
    public CheckIfEquipmentComplete() {
    }

    @Override
    protected void operation() {
        boolean complete = true;
        for (Kit kit : stateKeeper.getValue().kits()) {
            if (!isKitComplete(kit)) {
                complete = false;
            }
        }
        if (complete) {
            updateState();
        }
    }

    private boolean isKitComplete(Kit kit) {
        boolean complete = true;
        for (ShipmentTaskPosition item : kit.kitContent()){
            if(item.doneQuantity() < item.requiredQuantity()){
                return false;
            }
        }
        return complete;
    }

    private void updateState() {
        stateKeeper.change(state -> state.toBuilder()
                .completeState(Enums.CompleteState.comlete).build());
    }
}
