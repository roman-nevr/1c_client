package com.example.dmitry.a1c_client.presentation;

import android.support.v4.view.ViewPager;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.interactor.ChangePositionInteractor;
import com.example.dmitry.a1c_client.domain.interactor.Interactor;
import com.example.dmitry.a1c_client.domain.interactor.SetDisplayStateInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
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

    //TODO: cleanse out this class
    //---------Filters---------------
    protected Boolean isProgress(Shipable state) {
        return state.transmissionState() == requested;
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

    public void start() {
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

    public ShipmentTaskPosition getPosition(int index) {
        return viewState.get(index);
    }

    public void onQuantityChanges(String id, int quantity) {
        getChangeInteractor().setData(id, quantity).execute();
    }

    protected abstract ChangePositionInteractor getChangeInteractor();

    public void init() {
        boolean isInitiated = checkAndInitStateKeeper();
        if (isInitiated) {
            getUpdateInteractor().execute();
        }else {//else data have already downloaded
            fillView(getStateKeeperValue());
        }
    }

    protected abstract boolean checkAndInitStateKeeper();

    protected abstract Shipable getStateKeeperValue();

    protected abstract Interactor getUpdateInteractor();

    protected void addSubscription(Subscription subscription){
        subscriptions.add(subscription);
    }
}
