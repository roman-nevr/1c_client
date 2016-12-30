package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.ChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.Interactor;
import com.example.dmitry.a1c_client.domain.interactor.SetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.ShipmentChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.ShipmentSetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.UpdateShipmentTaskInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskPresenter extends BaseShipmentPresenter{
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject UpdateShipmentTaskInteractor updateInteractor;
    @Inject ShipmentChangePositionInteractor changeInteractor;
    @Inject ShipmentSetDisplayStateInteractor displayStateInteractor;


    @Inject
    public ShipmentTaskPresenter() { }

    @Override
    protected Observable<Shipable> getObservable() {
        return stateKeeper.getObservable().map(state -> (Shipable)state);
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

    @Override
    protected Interactor getUpdateInteractor() {
        return updateInteractor;
    }

    void setIdle() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(idle)
                .errorState(ok).build());
    }

    @Override
    protected SetDisplayStateInteractor getDisplayStateInteractor() {
        return displayStateInteractor;
    }


    @Override
    protected void clearState() {
        stateKeeper.update(ShipmentTaskState.EMPTY);
    }

    @Override
    public void start() {
        super.start();
        addSubscription(onDataDownloaded());
    }

    private Subscription onDataDownloaded(){
        return stateKeeper.getObservable()
                .filter(this::isDataDownloaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    view.hideProgress();
                    fillView(state);
                    setIdle();
                });
    }

    private Boolean isDataDownloaded(ShipmentTaskState taskState) {
        return taskState.transmissionState()==received && taskState.errorState() == ok;
    }
}
