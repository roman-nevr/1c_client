package com.example.dmitry.a1c_client.dummy;

import com.example.dmitry.a1c_client.domain.entity.Client;
import com.example.dmitry.a1c_client.domain.entity.Document;
import com.example.dmitry.a1c_client.domain.entity.Image;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.Unit;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by roma on 18.12.2016.
 */

public class Dummy {
    public static final List<Client> CLIENTS = new ArrayList<>();
    public static final List<Document> DOCUMENTS = new ArrayList<>();
    public static final List<NomenclaturePosition> NOMENCLATURE = new ArrayList<>();


    public static final Map<String, Client> CLIENT_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> NOMENCLATURE_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> POSITION_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> VENDOR_CODES_MAP = new HashMap<>();

    private static final int COUNT = 10;

    static {

        Client omegaClient = Client.builder().id("1").name("Омега").build();
        Client rogaClient = Client.builder().id("1").name("Рога и копыта").build();
        CLIENTS.add(omegaClient);
        CLIENTS.add(rogaClient);
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addNomenclature(createNomenclature(i));
            addDocument(createDummyDocument0(i));
            addDocument(createDummyDocument1(i));
        }

        POSITION_MAP.put("11111111", NomenclaturePosition.create("1", "First", "First description",
                "F111", "11111111", Image.EMPTY, Unit.DEFAULT_LIST));
        POSITION_MAP.put("11111111", NomenclaturePosition.builder().id("1").positionName("Первая").
                vendorCode("F111").description("111").units(Unit.DEFAULT_LIST).build());
        POSITION_MAP.put("11111112", NomenclaturePosition.builder().id("2").positionName("Second").
                vendorCode("F222").description("222").units(Unit.DEFAULT_LIST).build());
        POSITION_MAP.put("11111113", NomenclaturePosition.builder().id("3").positionName("Third").
                vendorCode("F333").description("333").units(Unit.DEFAULT_LIST).build());

        VENDOR_CODES_MAP.put("chul", NomenclaturePosition.builder().id("4").positionName("Чулки")
                .description("Чулки черные").vendorCode("chul").units(Unit.DEFAULT_LIST).build());
        VENDOR_CODES_MAP.put("abc", NomenclaturePosition.builder().id("5").positionName("Букварь")
                .description("Детский букварь").vendorCode("abc").units(Unit.DEFAULT_LIST).build());
        VENDOR_CODES_MAP.put("error", NomenclaturePosition.builder().id("6").positionName("error")
                .description("error").vendorCode("error").units(Unit.DEFAULT_LIST).build());
    }

    private static void addDocument(Document item) {
        DOCUMENTS.add(item);
    }

    private static Document createDummyDocument0(int i) {
        return Document.builder().id("AB" + i).client(CLIENTS.get(0)).docNumber(""+i).comment("")
                .docDate(new Date()).build();
    }

    private static Document createDummyDocument1(int i) {
        return Document.builder().id("AB" + i + COUNT).client(CLIENTS.get(1))
                .docNumber(""+i + COUNT).comment("").docDate(new Date()).build();
    }

    private static void addNomenclature(NomenclaturePosition item) {
        NOMENCLATURE.add(item);
        NOMENCLATURE_MAP.put(item.barCode(), item);
    }

    private static NomenclaturePosition createNomenclature(int i) {
        return NomenclaturePosition.builder().id(""+i).positionName("Товар "+i)
                .description("Описание товара "+i).vendorCode("AB"+i+"CD").barCode(""+i)
                .units(Unit.DEFAULT_LIST).image(Image.EMPTY).build();
    }
}
