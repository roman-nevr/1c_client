package com.example.dmitry.a1c_client.domain.interactor;


import android.util.Log;

import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeListState;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentListState;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.downloadError;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.progress;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.ready;


public class UpdateIncomeDocumentsInteractor extends Interactor {
    @Inject StateKeeper<IncomeListState> incomeListStateKeeper;
    @Inject DocumentRepository documentRepository;

    @Inject
    public UpdateIncomeDocumentsInteractor() {}

    @Override
    protected void operation() {
            try {
                showProgress();
                List<IncomeDocument> documents = loadDocuments();
                updateState(documents);
            } catch (Throwable throwable) {
                Log.d("income", throwable.getMessage());
                System.out.println("fromUpdateInteractor: " +  throwable.getMessage());
                throwable.printStackTrace();
                showError();
            }
    }

    private List<IncomeDocument> loadDocuments() throws Throwable {
        return documentRepository
                .getIncomeDocuments()
                .toBlocking()
                .value();
    }

    private void showError() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(downloadError).build());
    }

    private void showProgress() {
        incomeListStateKeeper.change(state -> state.toBuilder().state(progress).build());
    }

    private void updateState(List<IncomeDocument> documents) {
        incomeListStateKeeper.change(state -> state.toBuilder().documents(documents).state(ready).build());
    }
}
