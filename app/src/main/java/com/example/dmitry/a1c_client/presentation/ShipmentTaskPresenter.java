package com.example.dmitry.a1c_client.presentation;

import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateShipmentTaskInteractor;
import com.example.dmitry.a1c_client.domain.interactor.ShipmentChangePositionInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskPresenter {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject UpdateShipmentTaskInteractor updateInteractor;
    @Inject ShipmentChangePositionInteractor changeInteractor;
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
            updateInteractor.execute();
        }else {//else data have already downloaded
            viewState = new ShipmentViewState(stateKeeper.getValue().positions(),
                    stateKeeper.getValue().whatToShow() == actual);
        }
    }

    public void start() {
        subscribeOnDataLoaded();
        subscribeOnProgress();
        subscribeOnDataChanges();
    }

    private void subscribeOnDataChanges() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isDataChange)
                .map(state -> state.positions())
                .filter(this::isPositionsChange)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(positions -> {
                    viewState.update(positions);
                    if (!viewState.isLastVisiblePage()) {
                        if (viewState.hasMarkedItem()){
                            adapterHelper.removeVisiblePage();
                        }
                    }else if(viewState.hasMarkedItem()){
                        view.onComplete();
                    }
                }));
    }

    private void fillViews(ShipmentViewState viewState) {
        view.initProgressBar(viewState.actualPositions.size(),
                viewState.initialPositions.size());
        ViewPager viewPager = view.getViewPager();
        adapterHelper = new ShipmentViewPagerAdapterHelper(viewPager,
                view.provideFragmentManager(), viewState);
        adapterHelper.bindAdapterAndPageChangeListener();
    }

    public void stop() {
        subscriptions.clear();
    }

    public ShipmentTaskPosition getPosition(int index) {
        return viewState.get(index);
    }

    public void onQuantityChanges(int index, int quantity) {
        changeInteractor.setData(viewState.get(index).position().id(), quantity).execute();
    }

    //------subscribe methods---------
    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }

    private void subscribeOnDataLoaded() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isDataLoaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    viewState = new ShipmentViewState(state.positions(),
                            state.whatToShow() == actual);
                    view.hideProgress();
                    fillViews(viewState);
                    setIdle();
                }));
    }

    private void setIdle() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(idle)
                .errorState(ok).build());
    }

    //---------Filters---------------
    private Boolean isProgress(ShipmentTaskState state) {
        return state.transmissionState() == requested;
    }

    private Boolean isDataLoaded(ShipmentTaskState taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
    }

    private Boolean isDataChange(ShipmentTaskState taskState) {
        return taskState.transmissionState() == idle
                && taskState.errorState() == ok
                && taskState.completeState() == notComplete;
    }

    private Boolean isPositionsChange(List<ShipmentTaskPosition> positions){
        return positions != viewState.initialPositions;
    }
}
