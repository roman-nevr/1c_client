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

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.barCodeNotFoundDialog;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.barCodeSavingTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.empty;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.newBarcodeDialog;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.ready;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.storagePlaceReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.storagePlaceTransmissionError;
import static com.example.dmitry.a1c_client.misc.CommonFilters.isValidNumber;

/**
 * Created by roma on 18.12.2016.
 */

public class IncomeTaskPresenter {
    @Inject
    IncomeTaskView view;
    @Inject
    StateKeeper<IncomeTaskState> stateKeeper;
    @Inject GetNomenclatureByBarCodeInteractor barCodeInteractor;
    @Inject GetStorageInteractor storageInteractor;
    private CompositeSubscription subscriptions;
    private SpinnerAdapter adapter;

    @Inject
    public IncomeTaskPresenter() {
    }

    public void startSubscriptions(EditText etBarCode, EditText etQuantity, Spinner spinner,
                                   Button btnShowMap, Button btnGetStorageInfo) {

        if(stateKeeper.getValue()==null){
            stateKeeper.update(IncomeTaskState.EMPTY);
        }
        subscriptions = new CompositeSubscription();
        subscribeOnBarCode(etBarCode);
        subscribeOnQuantity(etQuantity);
        subscribeOnSpinner(spinner);
        subscribeOnShowMapButton(btnShowMap);
        subscribeOnStorageInfoButton(btnGetStorageInfo);
        subscribeOnNewPositionReceived();
        subscribeOnStorageInfoReceved();
        subscribeOnPositionNotFound();
        subscribeOnNewBarCodeDialog();
        subscribeOnNetError();
        subscribeOnEmptyState();
        subscribeOnProgress();
    }


    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> view.showProgress()));
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


    private void subscribeOnNetError() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNetError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.showNetErrorMessage();
                }));
    }


    private void subscribeOnNewBarCodeDialog() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isNewBarCodeDialog)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.showNewBarCodeDialog();
                }));
    }


    private void subscribeOnPositionNotFound() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isPositionNotFound)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.hideProgress();
                    view.showPositionNotFoundDialog();
                }));
    }


    private void subscribeOnStorageInfoReceved() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isStorageInfoReceived)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(taskState -> {
                    view.hideProgress();
                    view.showStorageInfo(taskState.storagePlace(), taskState.storageElement());
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

    private void subscribeOnQuantity(EditText etQuantity) {
        subscriptions.add(RxTextView.textChanges(etQuantity).debounce(500, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidNumber)
                .map(s -> Integer.parseInt(s))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(quantity -> {
                    if (isQuantityChanged(quantity)){
                        stateKeeper.change(state -> state.toBuilder().quantity(quantity).build());
                        storageInteractor.execute();
                    }else {
                        view.showPosition(stateKeeper.getValue().position());
                    }
                }));
    }



    private void subscribeOnStorageInfoButton(Button btnGetStorageInfo) {
        subscriptions.add(RxView.clicks(btnGetStorageInfo)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(o -> {
                    if(isValidNumber(view.getQuantity())){
                        stateKeeper.change(state -> state.toBuilder()
                                .quantity(Integer.parseInt(view.getQuantity())).build());
                        storageInteractor.execute();
                    }else {
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
        subscriptions.add(RxAdapterView.itemSelections(spinner).filter(this::isSpinnerReady)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(i -> stateKeeper
                        .change(state -> state.toBuilder().unit(setNewUnit(state, i))
                                .build())));
    }

    private void subscribeOnBarCode(EditText etBarCode) {
        subscriptions.add(RxTextView.textChanges(etBarCode).debounce(1000, TimeUnit.MILLISECONDS)
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidBarCode)
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(barCode -> {
                    if(isBarCodeChanged(barCode)){
                        barCodeInteractor.setBarCode(barCode).execute();
                    }
                }));
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

    public void onOkButton(int queryId) {

    }

    public void onCancelButton(int queryId) {

    }

    public void onMessageButton(int queryId) {

    }

    //filter methods
    private Boolean isPositionReceived(IncomeTaskState taskState) {
        return taskState.state() == positionReceived ? true : false;
    }

    private Boolean isStorageInfoReceived(IncomeTaskState taskState) {
        return taskState.state() == storagePlaceReceived ? true : false;
    }

    private Boolean isPositionNotFound(IncomeTaskState taskState) {
        return taskState.state() == barCodeNotFoundDialog ? true : false;
    }

    private Boolean isNewBarCodeDialog(IncomeTaskState taskState) {
        return taskState.state() == newBarcodeDialog ? true : false;
    }

    private Boolean isNetError(IncomeTaskState taskState) {
        return (taskState.state() == positionTransmissionError
                || taskState.state() == barCodeSavingTransmissionError
                || taskState.state() == storagePlaceTransmissionError) ? true : false;
    }

    private Boolean isEmptyState(IncomeTaskState taskState) {
        return (taskState.state() == empty) ? true : false;
    }

    private Boolean isSpinnerReady(Integer integer) {
        IncomeTaskState taskState = stateKeeper.getValue();
        if(taskState != null){
            IncomeTaskState.State state = taskState.state();
            return (integer >= 0 && (state == positionReceived || state == storagePlaceReceived)) ?
                    true : false;
        }else {
            return false;
        }
    }

    private Boolean isProgress(IncomeTaskState taskState) {
        return taskState.state() == progress ? true : false;
    }

    private boolean isBarCodeChanged(String barCode){
        return !stateKeeper.getValue().position().barCode().equals(barCode);
    }

    private boolean isQuantityChanged(Integer quantity) {
        return quantity != stateKeeper.getValue().quantity();
    }

}
