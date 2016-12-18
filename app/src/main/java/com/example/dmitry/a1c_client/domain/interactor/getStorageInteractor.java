package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.ready;

/**
 * Created by roma on 18.12.2016.
 */

public class getStorageInteractor extends Interactor {
    @Inject
    StateKeeper<IncomeTaskState> stateKeeper;
    @Inject
    IncomeTaskRepository taskRepository;
    @Override
    protected void operation() {

    }

    private void showError() {
        stateKeeper.change(state -> state.builder()
                .state(positionTransmissionError).build());
    }

    private void updateState(NomenclaturePosition position) {
        stateKeeper.change(state -> state.builder()
                .position(position).state(ready).build());
    }

    private IncomeTaskState getStorage(IncomeTaskState state){
        return taskRepository.getStorageInfo(state).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.builder().state(progress).build());
    }
}
