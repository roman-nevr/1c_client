package com.example.dmitry.a1c_client.presentation.document_list;


import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.presentation.interfaces.DocumentsListView;

import java.util.List;

public interface EquipListView extends DocumentsListView {
    void setDocuments(List<EquipDocument> documents);
}
