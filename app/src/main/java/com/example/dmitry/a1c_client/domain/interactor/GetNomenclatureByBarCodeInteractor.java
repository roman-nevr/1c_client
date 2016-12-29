package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.notFound;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.barCodeInput;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.displayPosition;

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
                showBarCodeNoFound();
            }
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showError();
        }
    }

    private void showBarCodeNoFound() {
        incomeTaskStateStateKeeper.change(state -> state
                .toBuilder()
                //.position(state.position().toBuilder().barCode(barCode).build())
                .position(NomenclaturePosition.EMPTY.toBuilder().barCode(barCode).build())
                .positionState(notFound)
                .storageState(idle)
                .errorState(ok)
                .build());
    }

    private void updateState(NomenclaturePosition position) {
        incomeTaskStateStateKeeper.change(state ->
                state.toBuilder()
                        .position(position)
                        .viewState(displayPosition)
                        .positionState(received)
                        .storageState(idle)
                        .errorState(ok)
                        .build());
    }

    private void showError() {
        incomeTaskStateStateKeeper.change(state ->
            state.toBuilder()
                    .positionState(error)
                    .errorState(connectionError)
                    .build()
        );
    }

    private NomenclaturePosition getPosition() {
        return taskRepository.getPositionByBarCode(barCode).toBlocking().value();
    }

    private void showProgress() {
        incomeTaskStateStateKeeper.change(state -> state.toBuilder()
                .viewState(barCodeInput)
                .positionState(requested)
                .storageState(idle)
                .errorState(ok)
                .build());
    }


}
