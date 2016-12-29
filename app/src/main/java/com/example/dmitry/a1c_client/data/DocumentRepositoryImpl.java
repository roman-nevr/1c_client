package com.example.dmitry.a1c_client.data;


import com.example.dmitry.a1c_client.domain.DocumentRepository;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Single;

public class DocumentRepositoryImpl implements DocumentRepository {
    @Override
    public Single<List<ShipmentDocument>> getShipmentDocuments() {
        /*List<Document> newList = new ArrayList<>();
        Client omegaClient = Client.builder().id("1").name("Омега").build();
        Client rogaClient = Client.builder().id("1").name("Рога и копыта").build();
        newList.add(Document.builder().client(omegaClient).id("doc1").docDate(new Date()).docNumber("doc1").comment("").build());
        newList.add(Document.builder().client(omegaClient).id("doc2").docDate(new Date()).docNumber("doc2").comment("").build());
        newList.add(Document.builder().client(rogaClient).id("doc3").docDate(new Date()).docNumber("doc3").comment("").build());*/

        return Single
                .just(Collections.unmodifiableList(Dummy.SHIPMENT_DOCUMENTS))
                .delay(1, TimeUnit.SECONDS);
    }

    @Override
    public Single<List<IncomeDocument>> getIncomeDocuments() {
        return Single.just(Collections.unmodifiableList(Dummy.INCOME_DOCUMENTS))
                .delay(1, TimeUnit.SECONDS);
    }

    @Override
    public Single<List<EquipDocument>> getEquipDocuments() {
        return Single.just(Collections.unmodifiableList(Dummy.EQUIP_DOCUMENTS))
                .delay(1, TimeUnit.SECONDS);
    }
}
