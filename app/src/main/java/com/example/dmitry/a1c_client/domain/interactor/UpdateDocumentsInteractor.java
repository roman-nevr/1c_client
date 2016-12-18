package com.example.dmitry.a1c_client.domain.interactor;


import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Document;
import com.example.dmitry.a1c_client.domain.entity.IncomeListState;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.error;
import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.ready;

public class UpdateDocumentsInteractor extends Interactor {
    @Inject StateKeeper<IncomeListState> incomeListStateKeeper;
    @Inject DocumentRepository documentRepository;

    @Inject
    public UpdateDocumentsInteractor() {}

    @Override
    protected void operation() {
            try {
                showProgress();
                List<Document> documents = loadDocuments();
                updateState(documents);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                showError();
            }
    }

    private List<Document> loadDocuments() throws Throwable {
        return documentRepository
                .getDocuments()
                .toBlocking()
                .value();
    }

    private void showError() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(error).build());
    }

    private void showProgress() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(progress).build());
    }

    private void updateState(List<Document> documents) {
        incomeListStateKeeper.change(state -> state.toBuilder().documents(documents).state(ready).build());
    }
}
