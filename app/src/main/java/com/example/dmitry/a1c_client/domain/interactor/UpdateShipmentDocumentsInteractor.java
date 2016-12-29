package com.example.dmitry.a1c_client.domain.interactor;


import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentListState;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.downloadError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.progress;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.ready;


public class UpdateShipmentDocumentsInteractor extends Interactor {
    @Inject StateKeeper<ShipmentListState> incomeListStateKeeper;
    @Inject DocumentRepository documentRepository;

    @Inject
    public UpdateShipmentDocumentsInteractor() {}

    @Override
    protected void operation() {
            try {
                showProgress();
                List<ShipmentDocument> documents = loadDocuments();
                updateState(documents);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                showError();
            }
    }

    private List<ShipmentDocument> loadDocuments() throws Throwable {
        return documentRepository
                .getShipmentDocuments()
                .toBlocking()
                .value();
    }

    private void showError() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(downloadError).build());
    }

    private void showProgress() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(progress).build());
    }

    private void updateState(List<ShipmentDocument> documents) {
        incomeListStateKeeper.change(state -> state.toBuilder().documents(documents).state(ready).build());
    }
}
