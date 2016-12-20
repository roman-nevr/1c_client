package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.android.fragments.NewBarCodeDialogFragment;
import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.interactor.GetNomenclatureByVendorCodeInteractor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionReceived;

/**
 * Created by Admin on 20.12.2016.
 */

public class NewBarCodeDialogPresenter {
    @Inject IncomeTaskRepository repository;
    @Inject GetNomenclatureByVendorCodeInteractor interactor;
    private NewBarCodeDialogView view;
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    private CompositeSubscription subscriptions;

    @Inject
    public NewBarCodeDialogPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void setView(NewBarCodeDialogView view){
        this.view = view;
    }

    public void start(){
        subscribeOnVendorCode();
        subscribeOnPosition();
        subscribeOnNetError();
    }

    private void subscribeOnPosition() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isPositionReceived)
                .map(taskState -> taskState.position())
                .subscribe(position -> {view.showNomenklatura(position);}));
    }

    private Boolean isPositionReceived(IncomeTaskState taskState) {
        return taskState.state() == positionReceived;
    }

    private void subscribeOnVendorCode() {
        subscriptions.add(view.getVendorCodeObservable()
                .debounce(1000, TimeUnit.MILLISECONDS)
                .map(chars -> chars.toString())
                .subscribe(vendorCode -> {
                    interactor.setVendorCode(vendorCode).execute();
                }));
    }

    public void stop(){
        subscriptions.clear();
    }

    public void onYesButtonClick(){

    }

    public void onCancelButtonClick(){

    }
}
