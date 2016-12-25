package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayStorageInfo;

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
                .storageState(error)
                .errorState(connectionError)
                .build());
    }

    private void updateState(IncomeTaskState taskState) {
        stateKeeper.change(state -> state.toBuilder()
                .storageElement(taskState.storageElement())
                .storagePlace(taskState.storagePlace())
                .storageState(received)
                .errorState(ok)
                .build());
    }

    private IncomeTaskState getStorage(IncomeTaskState state){
        return taskRepository.getStorageInfo(state).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder()
                .viewState(displayStorageInfo)
                .storageState(requested)
                .build());
    }
}
