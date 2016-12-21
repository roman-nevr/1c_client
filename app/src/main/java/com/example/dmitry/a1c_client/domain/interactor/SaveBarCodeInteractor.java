package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import java.io.IOException;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.barCodeSaved;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.barCodeSavingTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.progress;

/**
 * Created by Admin on 21.12.2016.
 */

public class SaveBarCodeInteractor extends Interactor {
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    @Inject IncomeTaskRepository taskRepository;
    private String barCode;

    @Inject
    public SaveBarCodeInteractor() { }

    @Override protected void operation() {
        if (barCode == null) {
            throw new IllegalArgumentException("barCode must not be null");
        }
        try {
            showProgress();
            IncomeTaskState newTaskState = saveBarCode(barCode);
            updateState(newTaskState);
        } catch (Throwable e) {
            e.printStackTrace();
            showError();
        }
    }

    public void setBarCode(String barCode) {
        this.barCode = barCode;
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .state(barCodeSavingTransmissionError).build());
    }

    private void updateState(IncomeTaskState taskState) {
        stateKeeper.change(state -> state.toBuilder()
                .position(state.position().toBuilder().barCode(barCode).build())
                .state(barCodeSaved).build());
    }

    private IncomeTaskState saveBarCode(String barCode) throws IOException {
        return taskRepository.saveBarCode(stateKeeper.getValue().position(), barCode)
                .toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder().state(progress).build());
    }

}
