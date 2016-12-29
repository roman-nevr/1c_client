package com.example.dmitry.a1c_client.presentation;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.EquipListFragment;
import com.example.dmitry.a1c_client.android.IncomeListFragment;
import com.example.dmitry.a1c_client.android.ShipmentListFragment;
import com.example.dmitry.a1c_client.android.equipment.CollectionTaskFragment;
import com.example.dmitry.a1c_client.android.equipment.EquipmentTaskFragment;
import com.example.dmitry.a1c_client.android.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.comlete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.notInitialased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.progress;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.collect;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.equip;

/**
 * Created by Admin on 29.12.2016.
 */
public class EquipmentTaskActivityPresenter {
    public static final String COLLECT_TAG = "Сбор элементов";
    public static final String EQUIP_TAG = "Комплектация";
    public static final int EQUIP_STAGE = 1;
    public static final int FINAL = 4;

    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject EquipmentView view;
    @Inject UpdateEquipmentTaskInteractor updateInteractor;
    private CompositeSubscription subscriptions;

    @Inject
    public EquipmentTaskActivityPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public Fragment getFragment(String tag) {
        return getFragmentByTag(tag);
    }

    public String getTag() {
        return (stateKeeper.getValue().stage() == collect ? COLLECT_TAG : EQUIP_TAG);
    }

    private Fragment getFragmentByTag(String tag) {
        List<Fragment> fragmentList = view.provideFragmentManager().getFragments();
        if (fragmentList != null) {
            for (Fragment fragment : fragmentList) {
                if ((fragment != null) && (fragment.getTag().equals(tag))) {
                    return fragment;
                }
            }
        }
        if (tag.equals(COLLECT_TAG)) {
            return new CollectionTaskFragment();
        }
        if (tag.equals(EQUIP_TAG)) {
            return new EquipmentTaskFragment();
        }
        return null;
    }

    public void init() {
        boolean success = stateKeeper.change(state -> {
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
        if (success) {
            updateInteractor.execute();
        }
    }

    public void start() {
        subscribeOnDataLoaded();
        //subscribeOnCollectionStage();
        //subscribeOnEquipStage();
    }

    private void subscribeOnEquipStage() {
        subscriptions.add(stateKeeper.getObservable()
        .filter(this::isEquipStage)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(state -> showEquipFragment()));
    }

    private void showEquipFragment() {
        Fragment fragment = getFragmentByTag(EQUIP_TAG);
        view.provideFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, EQUIP_TAG).commit();
    }

    private void subscribeOnCollectionStage() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isCollectionStage)
                .observeOn(AndroidSchedulers.mainThread()).
                        subscribe(state -> showCollectionFragment()));
    }

    private void showCollectionFragment() {
        Fragment fragment = getFragmentByTag(COLLECT_TAG);
        view.provideFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, COLLECT_TAG).commit();
    }

    public void stop() {
        subscriptions.clear();
    }

    private void subscribeOnDataLoaded() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isDataLoaded)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> {
                    showCollectionFragment();
                    view.hideProgress();
                    setIdle();
                }));
    }


    private Boolean isDataLoaded(EquipmentTaskState taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
    }

    private Boolean isCollectionStage(EquipmentTaskState taskState) {
        boolean result = taskState.stage() == collect
                && taskState.transmissionState() == received
                && comlete == notComplete;
        if(result){
            System.out.println("collect");
        }
        return result;
    }

    private Boolean isEquipStage(EquipmentTaskState taskState) {
        boolean result = taskState.stage() == equip
                && taskState.transmissionState() == received
                && comlete == notComplete;
        if(result){
            System.out.println("equip");
        }
        return result;
    }

    private void setIdle() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(idle)
                .errorState(ok).build());
    }

    public void onShipmentComplete() {
        DialogFragment fragment = MessageDialogFragment.newInstance("Все собрано, переходите к комплектации", EQUIP_STAGE);
        fragment.show(view.provideFragmentManager(), "ask");
    }


    public void prepareEquipStage() {
        wipeQuantity();
        showEquipFragment();
    }

    private void wipeQuantity() {

    }
}
