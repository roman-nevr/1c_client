package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.android.adapters.ShipmentViewPagerAdapterHelper;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateEquipmentTaskInteractor;
import com.example.dmitry.a1c_client.presentation.entity.ShipmentViewState;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 26.12.2016.
 */

public class CollectionPresenter{
    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject UpdateEquipmentTaskInteractor updateInteractor;
    private CompositeSubscription subscriptions;
    private ShipmentTaskView view;
    private ShipmentViewPagerAdapterHelper adapterHelper;
    private ShipmentViewState viewState;

    @Inject
    public CollectionPresenter() {
        subscriptions = new CompositeSubscription();
    }

    public void init() {
        boolean success = stateKeeper.change(state -> {
            if (state.completeState() == notInitailased) {
                return state.toBuilder()
                        .completeState(notComplete)
                        .transmissionState(requested).build();
            } else {
                return null;
            }
        });
        if (success) {
            updateInteractor.execute();
        }
    }

    public void start(){

    }
}
