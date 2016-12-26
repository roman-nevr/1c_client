package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.EquipmentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.presentation.entity.BarCodeEntryMap;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 26.12.2016.
 */

public class UpdateEquipmentTaskInteractor extends Interactor {

    @Inject StateKeeper<EquipmentTaskState> stateKeeper;
    @Inject EquipmentRepository repository;

    @Inject
    public UpdateEquipmentTaskInteractor() {}

    @Override
    protected void operation() {
        try {
            showProgress();
            List<Kit> kits = loadKits();
            updateState(kits);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            showError();
        }
    }

    private void updateState(List<Kit> kits) {
        stateKeeper.change(state -> state.toBuilder()
                .kits(kits)
                .barCodeEntryMap(new BarCodeEntryMap(kits))
                .completeState(notComplete)
                .transmissionState(received)
                .errorState(ok).build());
    }

    private List<Kit> loadKits() {
        return repository.getKits().toBlocking().value();
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .completeState(notInitailased)
                .transmissionState(error)
                .errorState(connectionError).build());
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder()
                .completeState(notComplete)
                .transmissionState(requested)
                .errorState(ok).build());
    }
}
