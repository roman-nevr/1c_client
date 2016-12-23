package com.example.dmitry.a1c_client.presentation;

import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.FillShipmentTaskInteractor;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskPresenter {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject FillShipmentTaskInteractor loadInteractor;
    private CompositeSubscription subscriptions;
    private ShipmentTaskView view;

    @Inject
    public ShipmentTaskPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void setView(ShipmentTaskView view){
        this.view = view;
    }

    public void start(){
        view.initProgressBar(getDone(stateKeeper), stateKeeper.getValue()
                .initialPositions().size());
        ViewPager viewPager = view.getViewPager();

    }

    private int getDone(StateKeeper<ShipmentTaskState> stateKeeper) {
        return stateKeeper.getValue().initialPositions().size()
                - stateKeeper.getValue().actualPositions().size();
    }

    public void stop(){
        subscriptions.clear();
    }
}
