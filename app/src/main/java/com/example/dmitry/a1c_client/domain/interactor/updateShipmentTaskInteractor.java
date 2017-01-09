package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.ShipmentTaskRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;

import rx.Single;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.connectionError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.error;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.requested;

/**
 * Created by Admin on 23.12.2016.
 */

public class UpdateShipmentTaskInteractor extends Interactor {
    @Inject StateKeeper<ShipmentTaskState> stateKeeper;
    @Inject ShipmentTaskRepository repository;

    @Inject
    public UpdateShipmentTaskInteractor() {}

    private String id;
    public Interactor setId(String id){
        this.id = id;
        return this;
    }

    @Override
    protected void operation() {
        if(id == null){
            throw new UnsupportedOperationException();
        }
        try {
            showProgress();
            List<ShipmentTaskPosition> shipmentTaskPositions = loadPositions(id);
            updateState(shipmentTaskPositions);
        }catch (IOException e){
            e.printStackTrace();
            showError();
        }
    }

    private List<ShipmentTaskPosition> loadPositions(String id) throws IOException {
        return repository.getTask(id).toBlocking().value();
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(requested)
                .errorState(ok)
                .build());
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder()
                .transmissionState(error)
                .errorState(connectionError)
                .build());
    }

    private void updateState(List<ShipmentTaskPosition> shipmentTaskPositions) {
        stateKeeper.change(state -> state.toBuilder()
                .positions(shipmentTaskPositions)
                .completeState(notComplete)
                .transmissionState(received)
                .errorState(Enums.ErrorState.ok)
                .build());
    }
}
