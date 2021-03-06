package com.example.dmitry.a1c_client.domain;


import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;

import java.io.IOException;
import java.util.List;

import rx.Single;

public interface DocumentRepository {
    Single<List<ShipmentDocument>> getShipmentDocuments() throws IOException;
    Single<List<IncomeDocument>> getIncomeDocuments() throws IOException;
    Single<List<EquipDocument>> getEquipDocuments() throws IOException;
}
