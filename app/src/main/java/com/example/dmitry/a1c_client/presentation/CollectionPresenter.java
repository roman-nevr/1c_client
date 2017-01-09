package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.interactor.ChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.CollectionChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.CollectionSetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.EquipmentWipeInteractor;
import com.example.dmitry.a1c_client.domain.interactor.Interactor;
import com.example.dmitry.a1c_client.domain.interactor.SetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;

import javax.inject.Inject;

import rx.Observable;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 26.12.2016.
 */

public class CollectionPresenter extends BaseShipmentPresenter{
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject UpdateEquipmentTaskInteractor updateInteractor;
    @Inject EquipmentWipeInteractor wipeInteractor;
    @Inject CollectionChangePositionInteractor changeInteractor;
    @Inject CollectionSetDisplayStateInteractor displayStateInteractor;

    @Inject
    public CollectionPresenter() { }

    @Override
    protected Observable<Shipable> getObservable() {
        return stateKeeper.getObservable().map(state -> (Shipable)state);
    }

    @Override
    protected void clearState() {
        wipeInteractor.execute();
        //stateKeeper.update(EquipmentTaskState.EMPTY);
    }



    @Override
    protected SetDisplayStateInteractor getDisplayStateInteractor() {
        return displayStateInteractor;
    }

    @Override
    protected ChangePositionInteractor getChangeInteractor() {
        return changeInteractor;
    }

    @Override
    protected boolean checkAndInitStateKeeper() {
        return stateKeeper.change(state -> {
            if (state.completeState() == notInitailased) {
                return state.toBuilder()
                        .completeState(notComplete)
                        .transmissionState(requested).build();
            } else {
                return null;
            }
        });
    }

    @Override
    protected Shipable getStateKeeperValue() {
        return stateKeeper.getValue();
    }


}
