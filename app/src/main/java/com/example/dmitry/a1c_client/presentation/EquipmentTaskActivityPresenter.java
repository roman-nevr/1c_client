package com.example.dmitry.a1c_client.presentation;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.equipment.CollectionTaskFragment;
import com.example.dmitry.a1c_client.android.views.equipment.EquipmentTaskFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;

import java.util.List;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.comlete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.collect;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.equip;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.notInitialised;

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
                String tag = stateKeeper.getValue().stage() == collect ? COLLECT_TAG : EQUIP_TAG;
                showFragmentByTag(tag);
            }
        }
    }

    private void showFragmentByTag(String tag) {
        Fragment fragment = getFragmentByTag(tag);
        view.provideFragmentManager().beginTransaction().add(fragment, tag);
    }

    public void start() {
        subscribeOnDataLoaded();
        subscribeOnProgress();
    }

    private void subscribeOnProgress() {
        subscriptions.add(stateKeeper.getObservable()
                .filter(this::isProgress)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(state -> view.showProgress()));
    }

    private Boolean isProgress(EquipmentTaskState state) {
        return state.stage() == notInitialised || state.transmissionState() == requested;
    }


    private void showEquipFragment() {
        Fragment fragment = getFragmentByTag(EQUIP_TAG);
        view.provideFragmentManager().beginTransaction()
                .replace(R.id.main_container, fragment, EQUIP_TAG).commit();
    }

    private void showCollectionFragment() {
        Fragment fragment = getFragmentByTag(COLLECT_TAG);
        view.provideFragmentManager().beginTransaction()
                .add(R.id.main_container, fragment, COLLECT_TAG).commit();
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
                    subscriptions.clear();
                }));
    }

    private Boolean isDataLoaded(EquipmentTaskState taskState) {
        return taskState.transmissionState() == received
                && taskState.completeState() == notComplete;
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
        showEquipFragment();
    }

}
