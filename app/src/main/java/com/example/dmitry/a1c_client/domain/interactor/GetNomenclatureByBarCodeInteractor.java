package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.barCodeNotFoundDialog;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.positionReceived;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.positionTransmissionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.progress;

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

    public Interactor setBarCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    @Override
    protected void operation() {
        try {
            showProgress();
            NomenclaturePosition position = getPosition();
            if (position != NomenclaturePosition.EMPTY) {
                updateState(position);
            } else {
                showPositionNoFound();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showError();
        }
    }

    private void showPositionNoFound() {
        incomeTaskStateStateKeeper.change(state -> state
                .toBuilder()
                .position(state.position().toBuilder().barCode(barCode).build())
                .viewState(barCodeNotFoundDialog)
                .build());
    }

    private void showError() {
        incomeTaskStateStateKeeper.change(state -> {

            return state.toBuilder()
                    .viewState(positionTransmissionError)
                    .build();
        });
    }

    private void updateState(NomenclaturePosition position) {
        incomeTaskStateStateKeeper.change(state -> {
            IncomeTaskState newState = state
                    .toBuilder()
                    .position(position)
                    .viewState(positionTransmissionError)
                    .build();
            return state.toBuilder()
                    .position(position)
                    .viewState(positionReceived)
                    .build();
        });
    }

    private NomenclaturePosition getPosition() {
        return taskRepository.getPositionByBarCode(barCode).toBlocking().value();
    }

    private void showProgress() {
        incomeTaskStateStateKeeper.change(state -> state.toBuilder().viewState(progress).build());
    }


}
