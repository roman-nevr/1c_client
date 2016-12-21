package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.interactor.GetNomenclatureByVendorCodeInteractor;
import com.example.dmitry.a1c_client.domain.interactor.SaveBarCodeInteractor;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.barCodeSavingTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionTransmissionError;

/**
 * Created by Admin on 20.12.2016.
 */

public class NewBarCodeDialogPresenter {
    @Inject IncomeTaskRepository repository;
    @Inject GetNomenclatureByVendorCodeInteractor getInteractor;
    @Inject SaveBarCodeInteractor saveInteractor;
    private NewBarCodeDialogView view;
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    private CompositeSubscription subscriptions;
    private String barCode;

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

    public void setBarCode(String barCode){
        this.barCode = barCode;
    }

    private void subscribeOnNetError() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNetError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> view.showError()));
    }

    private Boolean isNetError(IncomeTaskState taskState) {
        return (taskState.state() == positionTransmissionError
                || taskState.state() == barCodeSavingTransmissionError);
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
                    getInteractor.setVendorCode(vendorCode).execute();
                }));
    }

    public void stop(){
        subscriptions.clear();
    }

    public void onYesButtonClick(){
        saveInteractor.setBarCode(barCode);
    }

    public void onCancelButtonClick(){
        stateKeeper.update(IncomeTaskState.EMPTY);
        view.dismiss();
    }
}
