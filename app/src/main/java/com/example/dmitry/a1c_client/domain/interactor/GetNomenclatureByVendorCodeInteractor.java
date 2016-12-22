package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.error;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.received;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.requested;

/**
 * Created by roma on 18.12.2016.
 */

public class GetNomenclatureByVendorCodeInteractor extends Interactor {
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    @Inject IncomeTaskRepository taskRepository;
    private String vendorCode;

    @Inject
    public GetNomenclatureByVendorCodeInteractor() {
    }

    public Interactor setVendorCode(String vendorCode){
        this.vendorCode = vendorCode;
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
        stateKeeper.change(state -> state.toBuilder()
                .positionState(error)
                .errorState(connectionError)
                .build());
    }

    private void updateState(NomenclaturePosition position) {
        stateKeeper.change(state -> state.toBuilder()
                .position(position)
                .positionState(received)
                .errorState(ok)
                .build());
    }

    private NomenclaturePosition getPosition() {
        return taskRepository.getPositionByVendorCode(vendorCode).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder()
                .positionState(requested)
                .build());
    }
}
