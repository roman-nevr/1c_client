package com.example.dmitry.a1c_client.misc.dummy;

import com.example.dmitry.a1c_client.domain.entity.Client;
import com.example.dmitry.a1c_client.domain.entity.Document;
import com.example.dmitry.a1c_client.domain.entity.Image;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.Unit;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.dmitry.a1c_client.domain.entity.Unit.builder;

/**
 * Created by roma on 18.12.2016.
 */

public class Dummy {
    public static final List<Client> CLIENTS = new ArrayList<>();
    public static final List<Document> DOCUMENTS = new ArrayList<>();
    public static final List<NomenclaturePosition> NOMENCLATURE = new ArrayList<>();

    public static final List<Unit> DEFAULT_LIST =
            Collections.unmodifiableList(getDefaultList());


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
                "F111", "11111111", Image.EMPTY, Dummy.DEFAULT_LIST));
        POSITION_MAP.put("11111112", NomenclaturePosition.create("2", "Second", "Second description",
                "F112", "11111112", Image.EMPTY, Dummy.DEFAULT_LIST));
        POSITION_MAP.put("11111113", NomenclaturePosition.create("3", "Third", "Third description",
                "F113", "11111113", Image.EMPTY, Dummy.DEFAULT_LIST));

        VENDOR_CODES_MAP.put("chul", NomenclaturePosition.create("5", "Чулки", "Черные чулки",
                "chul", "11111115", Image.EMPTY, Dummy.DEFAULT_LIST));
        VENDOR_CODES_MAP.put("abc", NomenclaturePosition.create("6", "Букварь", "Детский букварь",
                "abc", "11111116", Image.EMPTY, Dummy.DEFAULT_LIST));
        VENDOR_CODES_MAP.put("error", NomenclaturePosition.create("7", "error", "Детский букварь",
                "error", "11111117", Image.EMPTY, Dummy.DEFAULT_LIST));
    }

    private static List<Unit> getDefaultList() {
        List<Unit> result = new ArrayList<>();
        result.add(builder().id("1").name("шт.").build());
        result.add(builder().id("2").name("уп.").build());
        return result;
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
                .units(Dummy.DEFAULT_LIST).image(Image.EMPTY).build();
    }
}
