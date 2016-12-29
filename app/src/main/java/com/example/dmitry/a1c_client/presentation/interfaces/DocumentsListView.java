package com.example.dmitry.a1c_client.presentation.interfaces;

/**
 * Created by Admin on 29.12.2016.
 */
public interface DocumentsListView {
    void showProgress();

    void hideProgress();

    void showError();

    void hideError();

    void showDocuments();

    void hideDocuments();
}
