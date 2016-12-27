package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.interactor.CollectionChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.CollectionSetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.SetDisplayStateInteractor;
import com.example.dmitry.a1c_client.domain.interactor.ShipmentChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import javax.inject.Inject;

import rx.Observable;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 26.12.2016.
 */

public class CollectionPresenter extends BaseShipmentPresenter{
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject UpdateEquipmentTaskInteractor updateInteractor;
    @Inject CollectionChangePositionInteractor changeInteractor;
    @Inject CollectionSetDisplayStateInteractor displayStateInteractor;
    private CompositeSubscription subscriptions;
    private ShipmentTaskView view;
    private ShipmentViewPagerAdapterHelper adapterHelper;
    private ShipmentViewState viewState;

    @Inject
    public CollectionPresenter() {
        subscriptions = new CompositeSubscription();
    }

    @Override
    protected Observable<Shipable> getObservable() {
        return stateKeeper.getObservable().map(state -> (Shipable)state);
    }

    @Override
    protected void clearState() {

    }

    @Override
    protected void setIdle() {

    }

    @Override
    protected SetDisplayStateInteractor getDisplayStateInteractor() {
        return displayStateInteractor;
    }

}
