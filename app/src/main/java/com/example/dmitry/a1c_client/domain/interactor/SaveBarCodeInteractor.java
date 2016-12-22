package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import java.io.IOException;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.noRights;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.error;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.received;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayPosition;

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
            boolean success = saveBarCode(barCode);
            updateState(success);
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
                .positionState(error).errorState(connectionError).build());
    }

    private void updateState(boolean success) {
        if (success) {
            stateKeeper.change(state -> state.toBuilder().position(state.position()
                            .toBuilder()
                            .barCode(barCode).build())
                    .viewState(displayPosition)
                    .positionState(received)
                    .errorState(ok).build());
        } else {
            stateKeeper.change(state -> state.toBuilder()
                    .positionState(error)
                    .errorState(noRights)
                    .build());
        }
    }

    private boolean saveBarCode(String barCode) throws IOException {
        return taskRepository.saveBarCode(stateKeeper.getValue().position(), barCode)
                .toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder().positionState(requested).build());
    }

}
