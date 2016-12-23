package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.interactor.GetNomenclatureByVendorCodeInteractor;
import com.example.dmitry.a1c_client.domain.interactor.SaveBarCodeInteractor;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.noRights;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.notFound;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayPosition;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.newBarcodeDialog;


/**
 * Created by Admin on 20.12.2016.
 */

public class NewBarCodeDialogPresenter {
    @Inject IncomeTaskRepository repository;
    @Inject GetNomenclatureByVendorCodeInteractor getInteractor;
    @Inject SaveBarCodeInteractor saveInteractor;
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    private NewBarCodeDialogView view;
    private CompositeSubscription subscriptions;
    private String barCode;

    @Inject
    public NewBarCodeDialogPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void setView(NewBarCodeDialogView view) {
        this.view = view;
    }

    public void start() {
        stateKeeper.change(state -> state.toBuilder().positionState(notFound).build());
        subscribeOnVendorCodeInput();
        subscribeOnPosition();
        subscribeOnNetError();
        subscribeOnVendorCodeNotFound();
        subscribeOnBarCodeSaved();
        subscribeOnNoRightsError();
    }

    private void subscribeOnNoRightsError() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNoRightsError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.showError("У вас нет прав на эту операцию");
                }));
    }

    private void subscribeOnBarCodeSaved() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNewBarCodeSaved)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.dismiss();
                }));
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private void subscribeOnNetError() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNetError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(t -> view.showError("Нет связи с сервером")));
    }

    private Boolean isNetError(IncomeTaskState taskState) {
        return (taskState.viewState() == newBarcodeDialog
                && taskState.positionState() == error
                && taskState.errorState() == connectionError);
    }

    private Boolean isNewBarCodeSaved(IncomeTaskState taskState) {
        return (taskState.viewState() == displayPosition
                && taskState.positionState() == received);
    }

    private Boolean isNoRightsError(IncomeTaskState taskState) {
        return (taskState.viewState() == newBarcodeDialog
                && taskState.positionState() == error
                && taskState.errorState() == noRights);
    }

    private void subscribeOnPosition() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isPositionReceived)
                .map(taskState -> taskState.position())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(position -> {
                    view.showNomenklatura(position);
                    view.showYesButton();
                }));
    }

    private void subscribeOnVendorCodeNotFound() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isVendorCodeNotFound)
                .map(taskState -> taskState.position())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(position -> {
                    view.showNomenklatura(position);
                    view.hideYesButton();
                }));
    }

    private Boolean isVendorCodeNotFound(IncomeTaskState taskState) {
        return taskState.viewState() == newBarcodeDialog && taskState.positionState() == notFound;
    }

    private Boolean isPositionReceived(IncomeTaskState taskState) {
        return (taskState.viewState() == newBarcodeDialog && taskState.positionState() == received);
    }

    private void subscribeOnVendorCodeInput() {
        subscriptions.add(RxTextView.textChanges(view.etVendorCode())
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(this::isValidVendorCode)
                .map(chars -> chars.toString())
                .subscribe(vendorCode -> {
                    getInteractor.setVendorCode(vendorCode).execute();
                }));
    }

    private Boolean isValidVendorCode(CharSequence charSequence) {
        return charSequence.length() > 0;
    }

    public void stop() {
        subscriptions.clear();
    }

    public void onYesButtonClick() {
        saveInteractor.setBarCode(barCode).execute();
    }

    public void onCancelButtonClick() {
        stateKeeper.update(IncomeTaskState.EMPTY);
        view.dismiss();
    }
}
