package com.example.dmitry.a1c_client.presentation;

import android.content.Context;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.adapters.UnitSpinnerAdapter;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState;
import com.example.dmitry.a1c_client.domain.entity.Unit;
import com.example.dmitry.a1c_client.domain.interactor.GetNomenclatureByBarCodeInteractor;
import com.example.dmitry.a1c_client.domain.interactor.GetStorageInteractor;
import com.example.dmitry.a1c_client.misc.CommonFilters;
import com.jakewharton.rxbinding.view.RxView;
import com.jakewharton.rxbinding.widget.RxAdapterView;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.notFound;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.barCodeInput;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayPosition;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayStorageInfo;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.newBarcodeDialog;
import static com.example.dmitry.a1c_client.misc.CommonFilters.isValidNumber;

/**
 * Created by roma on 18.12.2016.
 */

public class IncomeTaskPresenter {
    @Inject IncomeTaskView view;
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    @Inject GetNomenclatureByBarCodeInteractor barCodeInteractor;
    @Inject GetStorageInteractor storageInteractor;
    private CompositeSubscription subscriptions;
    private SpinnerAdapter adapter;

    @Inject
    public IncomeTaskPresenter() {
        subscriptions = new CompositeSubscription();
        System.out.println(this);
    }


    public void startSubscriptions(EditText etBarCode, EditText etQuantity, Spinner spinner,
                                   Button btnShowMap, Button btnGetStorageInfo) {

        if (stateKeeper.getValue() == null) {
            setEmptyState();
        }

        subscribeOnProgress();
        subscribeOnNetError();
        subscribeOnEmptyState();

        subscribeOnBarCodeInput(etBarCode);

        subscribeOnPositionByBarCodeReceived();
        subscribeOnBarCodeNotFound();

        subscribeOnStorageInfoButton(btnGetStorageInfo);
        subscribeOnQuantityInput(etQuantity);
        subscribeOnSpinner(spinner);
        subscribeOnStorageInfoReceved();
        subscribeOnShowMapButton(btnShowMap);

        subscribeOnNewBarCodeDialog();
        subscribeOnPositionByVendorCodeReceived();
    }

    private void setEmptyState() {
        stateKeeper.update(IncomeTaskState.EMPTY);
    }

    private void subscribeOnPositionByBarCodeReceived() {
        subscribeOnNewPositionReceived();
    }

    private void subscribeOnPositionByVendorCodeReceived() {
        //subscribeOnNewPositionReceived();
    }

    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> view.showProgress()));
    }

    private void subscribeOnNetError() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNetError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.showNetErrorMessage();
                }));
    }

    private void subscribeOnEmptyState() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isEmptyState)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.hideProgress();
                    view.showEmptyState();
                }));
    }

    private void subscribeOnBarCodeInput(EditText etBarCode) {
        subscriptions.add(RxTextView.textChanges(etBarCode)
                .debounce(300, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidBarCode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barCode -> {
                    if (isBarCodeChanged(barCode)) {
                        barCodeInteractor.setBarCode(barCode).execute();
                    }
                }));
    }

    private void subscribeOnNewBarCodeDialog() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNewBarCodeViewState)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.showNewBarCodeDialog(stateKeeper.getValue().position().barCode());
                }));
    }


    private void subscribeOnBarCodeNotFound() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isBarCodeNotFound)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    stateKeeper.change(state -> state
                            .toBuilder()
                            .positionState(idle)
                            .build());
                    view.hideProgress();
                    view.showBarCodeNotFoundDialog();
                }));
    }


    private void subscribeOnStorageInfoReceved() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isStorageInfoReceived)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.hideProgress();
                    view.showStorageInfo(taskState.storagePlace(),
                            taskState.storageElement());
                }));
    }


    private void subscribeOnNewPositionReceived() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isPositionReceived)
                .map(IncomeTaskState::position)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(position -> {
                    view.hideProgress();
                    view.showPosition(position);
                }));
    }

    private void subscribeOnQuantityInput(EditText etQuantity) {
        subscriptions.add(RxTextView.textChanges(etQuantity)
                .debounce(500, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidNumber)
                .map(s -> Integer.parseInt(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(quantity -> {
                    if (isQuantityChanged(quantity)) {
                        stateKeeper.change(state -> state.toBuilder()
                                .quantity(quantity).build());
                        storageInteractor.execute();
                    } else {
                        view.hideProgress();
                        view.showPosition(stateKeeper.getValue().position());
                    }
                }));
    }


    private void subscribeOnStorageInfoButton(Button btnGetStorageInfo) {
        subscriptions.add(RxView.clicks(btnGetStorageInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if (isValidNumber(view.getQuantity())) {
                        stateKeeper.change(state -> state.toBuilder()
                                .quantity(Integer.parseInt(view.getQuantity()))
                                .build());
                        storageInteractor.execute();
                    } else {
                        view.showQuantityError();
                    }
                }));
    }

    private void subscribeOnShowMapButton(Button btnShowMap) {
        subscriptions.add(RxView.clicks(btnShowMap)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> showMap()));
    }


    private void subscribeOnSpinner(Spinner spinner) {
        subscriptions.add(RxAdapterView.itemSelections(spinner)
                .filter(this::isSpinnerReady)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> stateKeeper
                        .change(state -> state.toBuilder()
                                .unit(setNewUnit(state, i))
                                .build())));
    }

    public void stop() {
        subscriptions.clear();
    }

    public SpinnerAdapter provideSpinnerAdapter(Context context) {
        IncomeTaskState state = stateKeeper.getValue();
        adapter = new UnitSpinnerAdapter(context, R.layout.spinner_simple_item_layout,
                state.position().units());
        return adapter;
    }

    private void showMap() {
        view.showMap(stateKeeper.getValue().storeMapObject());
    }

    private Unit setNewUnit(IncomeTaskState state, int i) {
        return state.position().units().get(i);
    }

    public void onRetryAccept() {
    }

    public void onBarCodeNotFoundDialogAccept() {
        stateKeeper.change(state -> state.toBuilder()
                .viewState(newBarcodeDialog).positionState(idle).build());
        //view.showNewBarCodeDialog(stateKeeper.getValue().position().barCode());
    }

    public void onRetryDecline() {
    }

    public void onBarCodeNotFoundDialogDecline() {
        setEmptyState();
    }

    public void onNewBarCodeDialogDecline() {
        setEmptyState();
    }

    //filter methods
    private Boolean isBarCodeInputState(IncomeTaskState taskState) {
        return taskState.viewState() == barCodeInput;
    }

    private Boolean isPositionReceived(IncomeTaskState taskState) {
        return taskState.positionState() == received;
    }

    private Boolean isStorageInfoReceived(IncomeTaskState taskState) {
        return taskState.storageState() == received;
    }

    private Boolean isBarCodeNotFound(IncomeTaskState taskState) {
        return (taskState.viewState() == barCodeInput
                && taskState.positionState() == notFound);
    }

    private Boolean isNewBarCodeViewState(IncomeTaskState taskState) {
        return (taskState.viewState() == newBarcodeDialog
                && taskState.positionState() == idle);
    }

    private Boolean isNetError(IncomeTaskState taskState) {
        return taskState.viewState() == barCodeInput && (taskState.positionState() == error
                || taskState.storageState() == error
                || taskState.errorState() == connectionError);
    }

    private Boolean isEmptyState(IncomeTaskState taskState) {
        return (taskState.viewState() == barCodeInput
                && taskState.positionState() == idle);
    }

    private Boolean isSpinnerReady(Integer integer) {
        IncomeTaskState taskState = stateKeeper.getValue();
        if (taskState != null) {
            ViewState viewState = taskState.viewState();
            return (integer >= 0 && (
                    (viewState == displayPosition && taskState.positionState() == received) ||
                            (viewState == displayStorageInfo)));
        } else {
            return false;
        }
    }

    private Boolean isProgress(IncomeTaskState taskState) {
        return (taskState.viewState() == barCodeInput
                    && taskState.positionState() == requested)
                || (taskState.viewState() == displayPosition
                    && taskState.storageState() == requested)
                || (taskState.viewState() == displayStorageInfo
                    && taskState.storageState() == requested);
    }

    private boolean isBarCodeChanged(String barCode) {
        return !stateKeeper.getValue().position().barCode().equals(barCode);
    }

    private boolean isQuantityChanged(Integer quantity) {
        return quantity != stateKeeper.getValue().quantity();
    }

}
