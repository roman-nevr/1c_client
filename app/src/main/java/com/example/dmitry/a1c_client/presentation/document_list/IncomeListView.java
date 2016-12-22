package com.example.dmitry.a1c_client.presentation.document_list;


import com.example.dmitry.a1c_client.domain.entity.Document;

import java.util.List;

public interface IncomeListView {
    void setDocuments(List<Document> documents);

    void showProgress();

    void hideProgress();

    void showError();

    void hideError();

    void showDocuments();

    void hideDocuments();

}
