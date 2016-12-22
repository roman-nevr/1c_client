package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.storagePlaceReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.storagePlaceTransmissionError;

/**
 * Created by roma on 18.12.2016.
 */

public class GetStorageInteractor extends Interactor {
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    @Inject IncomeTaskRepository taskRepository;

    @Inject
    public GetStorageInteractor() {
    }

    @Override
    protected void operation() {
        try {
            showProgress();
            IncomeTaskState newTaskState = getStorage(stateKeeper.getValue());
            updateState(newTaskState);
        }catch (Throwable throwable){
            throwable.printStackTrace();
            showError();
        }
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .viewState(storagePlaceTransmissionError).build());
    }

    private void updateState(IncomeTaskState taskState) {
        stateKeeper.change(state -> state.toBuilder()
                .storageElement(taskState.storageElement()).storagePlace(taskState.storagePlace())
                .viewState(storagePlaceReceived).build());
    }

    private IncomeTaskState getStorage(IncomeTaskState state){
        return taskRepository.getStorageInfo(state).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder().viewState(progress).build());
    }
}
