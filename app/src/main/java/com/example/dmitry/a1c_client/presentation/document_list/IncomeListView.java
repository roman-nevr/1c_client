package com.example.dmitry.a1c_client.presentation.document_list;


import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.presentation.interfaces.DocumentsListView;

import java.util.List;

public interface IncomeListView extends DocumentsListView {
    void setDocuments(List<IncomeDocument> documents);
}
