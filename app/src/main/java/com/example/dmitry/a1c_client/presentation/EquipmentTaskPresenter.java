package com.example.dmitry.a1c_client.presentation;

import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;

import com.example.dmitry.a1c_client.android.adapters.EquipActTableAdapter;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.interactor.CheckIfEquipmentComplete;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskByBarCodeInteractor;
import com.example.dmitry.a1c_client.misc.CommonFilters;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.comlete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
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

    public static final int FINAL = 4;

    @Inject
    public EquipmentTaskPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void start() {
        subscribeOnBarCodeInput();
        subscribeOnKitToShow();
        subscribeOnBarCodeNotFound();
        subscribeOnComplete();
    }

    public void stop() {
        subscriptions.clear();
    }

    public void setView(EquipmentTaskView view) {
        this.view = view;
    }

    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }


    private void subscribeOnBarCodeNotFound() {
        subscriptions.add(stateKeeper.getObservable()
                .debounce(500, TimeUnit.MILLISECONDS)
                .filter(this::isError)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showError("Штрихкод в подборе не найден")));
    }


    private void subscribeOnComplete() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isComplete)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    showMessage("Положите в набор\n" + stateKeeper.getValue().kitToShow()
                            .kitName().positionName() + "\nи нажмите ОК для завершения", FINAL);
                }));
    }

    private void showMessage(String s, int id) {
        DialogFragment fragment = MessageDialogFragment.newInstance(s, id);
        fragment.show(view.fragmentManager(), "ask");
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
                        view.clearBarCode();
                    } else {
                        view.hideViews();
                    }
                }));
    }

    private void subscribeOnBarCodeInput() {
        subscriptions.add(view.getBarCodeObservable()
                .map(charSequence -> charSequence.toString())
                .filter(CommonFilters::isValidBarCode)
                .subscribe(barCode -> {
                    view.showBarCodeHint(barCode);
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

    //---------Filters-------------------

    private Boolean isError(EquipmentTaskState state) {
        return state.transmissionState() == notFound;
    }


    private Boolean isProgress(EquipmentTaskState state) {
        return state.transmissionState() == requested;
    }


    private Boolean isComplete(EquipmentTaskState state) {
        return state.completeState() == comlete;
    }
}
