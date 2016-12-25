package com.example.dmitry.a1c_client.presentation;

import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.FillShipmentTaskInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState.DisplayState.actual;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskPresenter {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject FillShipmentTaskInteractor loadInteractor;
    private CompositeSubscription subscriptions;
    private ShipmentTaskView view;
    private ShipmentViewPagerAdapterHelper adapterHelper;
    private ShipmentViewState viewState;

    @Inject
    public ShipmentTaskPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void setView(ShipmentTaskView view) {
        this.view = view;
    }

    public void init() {
        boolean success = stateKeeper.change(state -> {
            if (state.completeState() == notInitailased) {
                return state.toBuilder()
                        .completeState(notComplete)
                        .transmissionState(requested).build();
            } else {
                return null;
            }
        });
        if (success) {
            loadInteractor.execute();
        }
    }

    public void start() {
        subscribeOnDataLoaded();
        subscribeOnProgress();
    }

    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }

    private Boolean isProgress(ShipmentTaskState state) {
        return state.transmissionState() == requested;
    }

    private void subscribeOnDataLoaded() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isDataLoaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    view.hideProgress();
                    fillViews(state);}));
    }

    private void fillViews(ShipmentTaskState state) {
        view.initProgressBar(getDone(state), state
                .initialPositions().size());
        ViewPager viewPager = view.getViewPager();
        viewState = new ShipmentViewState(state.initialPositions(),
                state.actualPositions(), state.whatToShow() == actual);
        adapterHelper = new ShipmentViewPagerAdapterHelper(viewPager,
                view.provideFragmentManager(), viewState);
        adapterHelper.bindAdapterAndPageChangeListener();
    }

    private Boolean isDataLoaded(ShipmentTaskState taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
    }

    private int getDone(ShipmentTaskState state) {
        return state.initialPositions().size()
                - state.actualPositions().size();
    }

    public void stop() {
        subscriptions.clear();
    }

    public ShipmentTaskPosition getPosition(int index) {
        return viewState.get(index);
    }
}
