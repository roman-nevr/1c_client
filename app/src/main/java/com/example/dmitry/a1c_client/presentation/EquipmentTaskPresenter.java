package com.example.dmitry.a1c_client.presentation;

import android.support.v7.widget.RecyclerView;

import com.example.dmitry.a1c_client.android.adapters.EquipActTableAdapter;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.interactor.CheckIfEquipmentComplete;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskByBarCodeInteractor;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;
import com.example.dmitry.a1c_client.misc.CommonFilters;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.notFound;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentTaskPresenter {
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject UpdateEquipmentTaskByBarCodeInteractor barCodeInputInteractor;
    @Inject CheckIfEquipmentComplete checkIfEquipmentCompleteInteractor;
    private EquipmentTaskView view;
    private CompositeSubscription subscriptions;
    private EquipActTableAdapter adapter;

    @Inject
    public EquipmentTaskPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void start() {
        subscribeOnBarCodeInput();
        subscribeOnDataDownload();
        subscribeOnKitToShow();
        subscribeOnProgress();
        subscribeOnBarCodeNotFound();
        subscribeOnComplete();
    }

    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }

    private Boolean isProgress(EquipmentTaskState state) {
        return state.transmissionState() == requested;
    }

    private void subscribeOnBarCodeNotFound() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showError("Штрихкод в подборе не найден")));
    }

    private Boolean isError(EquipmentTaskState state) {
        return state.transmissionState() == notFound;
    }

    private void subscribeOnComplete() {
        subscriptions.add(stateKeeper.getObservable()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.onComplete()));
    }

    private void subscribeOnKitToShow() {
        subscriptions.add(stateKeeper.getObservable()
                .map(state -> state.kitToShow())
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(kit -> {
                    if (kit != Kit.EMPTY) {
                        view.setViews(kit);
                        view.setAdapter(getAdapter(kit));
                    } else {
                        view.hideViews();
                    }
                }));
    }

    public void stop() {
        subscriptions.clear();
    }

    public void setView(EquipmentTaskView view) {
        this.view = view;
    }

    private void subscribeOnDataDownload() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isDataDownload)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    view.hideProgress();
                }));
    }

    private Boolean isDataDownload(EquipmentTaskState taskState) {
        return taskState.completeState() == notComplete
                && taskState.transmissionState() == received;
    }

    private void subscribeOnBarCodeInput() {
        subscriptions.add(view.getBarCodeObservable()
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidBarCode)
                .subscribe(barCode -> {
                    barCodeInputInteractor.setBarCode(barCode).execute();
                    checkIfEquipmentCompleteInteractor.execute();
                }));
    }

    private RecyclerView.Adapter getAdapter(Kit kit) {
        if (adapter == null) {
            adapter = new EquipActTableAdapter(kit.kitContent());
        } else {
            adapter.update(kit.kitContent());
        }
        return adapter;
    }
}
