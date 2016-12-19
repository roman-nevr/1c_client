package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.data.IncomeTaskRepositoryImpl;
import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.positionTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.ready;

/**
 * Created by roma on 18.12.2016.
 */

public class GetNomenclatureByBarCodeInteractor extends Interactor {
    @Inject StateKeeper<IncomeTaskState> incomeTaskStateStateKeeper;
    @Inject IncomeTaskRepository taskRepository;

    private String barCode;

    @Inject
    public GetNomenclatureByBarCodeInteractor() {
    }

    public Interactor setBarCode(String barCode){
        this.barCode = barCode;
        return this;
    }

    @Override
    protected void operation() {
        try {
            showProgress();
            NomenclaturePosition position = getPosition();
            updateState(position);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showError();
        }
    }

    private void showError() {
        incomeTaskStateStateKeeper.change(state -> {

            return state.toBuilder()
                .state(positionTransmissionError).build();});
    }

    private void updateState(NomenclaturePosition position) {
        incomeTaskStateStateKeeper.change(state -> {
            IncomeTaskState newState = state.toBuilder().position(position).state(positionTransmissionError).build();
            return state.toBuilder()
                .position(position).state(positionReceived).build();});
    }

    private NomenclaturePosition getPosition() {
        return taskRepository.getPositionByBarCode(barCode).toBlocking().value();
    }

    private void showProgress() {
        incomeTaskStateStateKeeper.change(state -> state.toBuilder().state(progress).build());
    }


}
