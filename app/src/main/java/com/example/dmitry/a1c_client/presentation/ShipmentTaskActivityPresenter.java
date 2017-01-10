package com.example.dmitry.a1c_client.presentation;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.shipment.ShipmentTaskFragment;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateShipmentTaskInteractor;

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
 * Created by Admin on 09.01.2017.
 */
public class ShipmentTaskActivityPresenter {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject WindowView view;
    @Inject UpdateShipmentTaskInteractor updateInteractor;
    private CompositeSubscription subscriptions;

    public static final String SHIPMENT_FRAGMENT = "shipment";
    public static final int FINAL = 4;

    @Inject
    public ShipmentTaskActivityPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void init(Bundle savedInstanceState, String id) {
        if (savedInstanceState == null) {
            boolean isNotUpToDate = stateKeeper.change(state -> {
                if (state.completeState() == notInitailased) {
                    return state.toBuilder()
                            .completeState(notComplete)
                            .transmissionState(requested)
                            .errorState(ok)
                            .whatToShow(actual)
                            .build();
                } else {
                    return null;
                }
            });
            if (isNotUpToDate) {
                updateInteractor.setId(id).execute();
            } else {//if updated
                showShipmentTaskFragment();
            }
        }
    }

    private void showShipmentTaskFragment(){
        ShipmentTaskFragment shipmentTaskFragment = new ShipmentTaskFragment();
        view.provideFragmentManager().beginTransaction().add(R.id.main_container,
                shipmentTaskFragment, SHIPMENT_FRAGMENT).commit();
    }

    public void start() {
        subscribeOnDataLoaded();
        subscribeOnProgress();
    }

    public void stop(){
        subscriptions.clear();
    }

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
                    showShipmentTaskFragment();
                    view.hideProgress();
                    setIdle();
                    subscriptions.clear();
                }));
    }

    private void setIdle() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(idle)
                .errorState(ok).build());
    }

    //--------------Filters
    private Boolean isProgress(ShipmentTaskState state) {
        return state.transmissionState() == requested;
    }

    private Boolean isDataLoaded(ShipmentTaskState taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
    }

    public void onShipmentComplete() {
        DialogFragment fragment = MessageDialogFragment
                .newInstance("Задание выполнено \nНажмите Ок для завершения", FINAL);
        fragment.show(view.provideFragmentManager(), "ask");
    }
}
