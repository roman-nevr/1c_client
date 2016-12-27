package com.example.dmitry.a1c_client.presentation;

import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.interactor.SetDisplayStateInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import java.util.List;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.all;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 27.12.2016.
 */
public abstract class BaseShipmentPresenter {
    protected ShipmentViewState viewState;
    protected ShipmentTaskView view;
    protected ShipmentViewPagerAdapterHelper adapterHelper;
    private CompositeSubscription subscriptions;

    public BaseShipmentPresenter() {
        subscriptions = new CompositeSubscription();
    }

    protected abstract Observable<Shipable> getObservable();

    //---------Filters---------------
    protected Boolean isProgress(Shipable state) {
        return state.transmissionState() == requested;
    }

    protected Boolean isDataLoaded(Shipable taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
    }

    protected Boolean isDataChange(Shipable taskState) {
        return taskState.transmissionState() == idle
                && taskState.errorState() == ok
                && taskState.completeState() == notComplete;
    }

    protected Boolean isPositionsChange(List<ShipmentTaskPosition> positions){
        return positions != viewState.initialPositions;
    }

    //------------------------------------------

    public void setView(ShipmentTaskView view) {
        this.view = view;
    }

    protected void fillView(Shipable state) {
        viewState = new ShipmentViewState(state.positions(),
                state.whatToShow() == actual);
        view.initProgressBar(viewState.initialPositions.size() - viewState.actualPositions.size(),
                viewState.initialPositions.size());
        ViewPager viewPager = view.getViewPager();
        adapterHelper = new ShipmentViewPagerAdapterHelper(viewPager,
                view.provideFragmentManager(), viewState);
        adapterHelper.bindAdapterAndPageChangeListener();
    }

    protected void changeData(List<ShipmentTaskPosition> positions) {
        viewState.update(positions);
        if (!viewState.isLastVisiblePage()) {
            if (viewState.hasMarkedItem()){
                view.incProgress();
                adapterHelper.removeVisiblePage();
            }
        }else if(viewState.hasMarkedItem()){
            clearState();
            view.onComplete();
        }
    }

    //------subscribe methods---------
    protected void subscribeOnProgress() {
        subscriptions.add(getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }

    protected void subscribeOnDataLoaded() {
        subscriptions.add(getObservable()
                .filter(this::isDataLoaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    view.hideProgress();
                    fillView(state);
                    setIdle();
                }));
    }

    protected void subscribeOnDataChanges() {
        subscriptions.add(getObservable()
                .filter(this::isDataChange)
                .map(state -> state.positions())
                .filter(this::isPositionsChange)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(positions -> {
                    changeData(positions);
                }));
    }

    //------------------------------------------

    protected abstract void clearState();

    protected abstract void setIdle();

    public void start() {
        subscribeOnDataLoaded();
        subscribeOnProgress();
        subscribeOnDataChanges();
    }

    public void stop() {
        view.getViewPager().setTag(viewState);
        subscriptions.clear();
    }

    public void showAllAccepted() {
        viewState.showOnlyActual = false;
        getDisplayStateInteractor().setDisplayState(all).execute();
        adapterHelper.notifyDataSetChanged();
    }

    protected abstract SetDisplayStateInteractor getDisplayStateInteractor();

    public void showAllDenied() {
        viewState.showOnlyActual = true;
        getDisplayStateInteractor().setDisplayState(actual).execute();
        adapterHelper.notifyDataSetChanged();
    }

}
