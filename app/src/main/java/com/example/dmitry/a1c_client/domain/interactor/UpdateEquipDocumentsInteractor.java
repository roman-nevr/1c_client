package com.example.dmitry.a1c_client.domain.interactor;


import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.EquipListState;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.downloadError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.progress;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.ready;


public class UpdateEquipDocumentsInteractor extends Interactor {
    @Inject StateKeeper<EquipListState> stateKeeper;
    @Inject DocumentRepository documentRepository;

    @Inject
    public UpdateEquipDocumentsInteractor() {}

    @Override
    protected void operation() {
            try {
                showProgress();
                List<EquipDocument> documents = loadDocuments();
                updateState(documents);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                showError();
            }
    }

    private List<EquipDocument> loadDocuments() throws Throwable {
        return documentRepository
                .getEquipDocuments()
                .toBlocking()
                .value();
    }

    private void showError() {
        stateKeeper.change(state -> state.toBuilder().state(downloadError).build());
    }

    private void showProgress() {
        stateKeeper.change(state -> state.toBuilder().state(progress).build());
    }

    private void updateState(List<EquipDocument> documents) {
        stateKeeper.change(state -> state.toBuilder().documents(documents).state(ready).build());
    }
}
