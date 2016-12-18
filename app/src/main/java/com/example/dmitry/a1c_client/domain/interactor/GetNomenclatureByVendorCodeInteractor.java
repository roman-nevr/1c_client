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

public class GetNomenclatureByVendorCodeInteractor extends Interactor {
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    @Inject IncomeTaskRepository taskRepository;
    private String vendorCode;

    public GetNomenclatureByVendorCodeInteractor(String vendorCode) {
        this.vendorCode = vendorCode;
    }

    @Override
    protected void operation() {
        try {
            showProgress();
            NomenclaturePosition position = getPosition();
            updateState(position);
        }catch (Throwable throwable){
            throwable.printStackTrace();
            showError();
        }
    }

    private void showError() {
        stateKeeper.change(state -> state.builder()
                .state(positionTransmissionError).build());
    }

    private void updateState(NomenclaturePosition position) {
        stateKeeper.change(state -> state.builder()
                .position(position).state(ready).build());
    }

    private NomenclaturePosition getPosition() {
        return taskRepository.getPositionByVendorCode(vendorCode).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.builder().state(progress).build());
    }
}
