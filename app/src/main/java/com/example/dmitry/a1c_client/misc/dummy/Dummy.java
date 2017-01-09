package com.example.dmitry.a1c_client.misc.dummy;

import com.example.dmitry.a1c_client.domain.entity.Client;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.domain.entity.Image;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
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
    public static final List<ShipmentDocument> SHIPMENT_DOCUMENTS = new ArrayList<>();
    public static final List<IncomeDocument> INCOME_DOCUMENTS = new ArrayList<>();
    public static final List<EquipDocument> EQUIP_DOCUMENTS = new ArrayList<>();
    public static final List<NomenclaturePosition> NOMENCLATURE = new ArrayList<>();
    public static final List<ShipmentTaskPosition> SHIPMENT_TASK = new ArrayList<>();
    public static final List<Kit> EQUIPMENT_TASK = new ArrayList<>();

    public static final List<Unit> DEFAULT_UNIT_LIST =
            Collections.unmodifiableList(getDefaultList());


    public static final Map<String, Client> CLIENT_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> NOMENCLATURE_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> POSITION_MAP = new HashMap<>();
    public static final Map<String, NomenclaturePosition> VENDOR_CODES_MAP = new HashMap<>();

    private static final int COUNT = 20;

    static {

        Client omegaClient = Client.builder().id("1").name("Омега").build();
        Client rogaClient = Client.builder().id("1").name("Рога и копыта").build();
        CLIENTS.add(omegaClient);
        CLIENTS.add(rogaClient);
        // Add some sample items.
        for (int i = 1; i <= COUNT; i++) {
            addNomenclature(createNomenclature(i));
            addDocument(createDummyShipmentDocument(i, CLIENTS.get(0)));
            addDocument(createDummyShipmentDocument(i, CLIENTS.get(1)));
        }

        for (int i = 1; i <= 5; i++) {
            SHIPMENT_TASK.add(createDummyShipment(i));
        }

        createEquipmentTask();
        
        createIncomeList();

        createEquipmentList();

        POSITION_MAP.put("11111111", NomenclaturePosition.create("1", "First", "First description",
                "F111", "11111111", Image.EMPTY, DEFAULT_UNIT_LIST));
        POSITION_MAP.put("11111112", NomenclaturePosition.create("2", "Second", "Second description",
                "F112", "11111112", Image.EMPTY, DEFAULT_UNIT_LIST));
        POSITION_MAP.put("11111113", NomenclaturePosition.create("3", "Third", "Third description",
                "F113", "11111113", Image.EMPTY, DEFAULT_UNIT_LIST));

        VENDOR_CODES_MAP.put("chul", NomenclaturePosition.create("5", "Чулки", "Черные чулки",
                "chul", "11111115", Image.EMPTY, DEFAULT_UNIT_LIST));
        VENDOR_CODES_MAP.put("abc", NomenclaturePosition.create("6", "Букварь", "Детский букварь",
                "abc", "11111116", Image.EMPTY, DEFAULT_UNIT_LIST));
        VENDOR_CODES_MAP.put("error", NomenclaturePosition.create("7", "error", "Детский букварь",
                "error", "11111117", Image.EMPTY, DEFAULT_UNIT_LIST));

    }

    private static void createEquipmentList() {
        for (int i = 1; i <= COUNT; i++){
            EQUIP_DOCUMENTS.add(createDummyEquipDocument(i, CLIENTS.get(0)));
        }
    }

    private static EquipDocument createDummyEquipDocument(int i, Client client) {
        return EquipDocument.create("id"+i, "Комплектация"+i, new Date(), client, "");
    }

    public static void addDummyEquipDocument(){
        EQUIP_DOCUMENTS.add(createDummyEquipDocument(EQUIP_DOCUMENTS.size(), CLIENTS.get(0)));
    }

    private static void createIncomeList() {
        for (int i = 1; i <= COUNT; i++){
            INCOME_DOCUMENTS.add(createDummyIncomeDocument(i));
        }
    }

    private static IncomeDocument createDummyIncomeDocument(int i) {
        return IncomeDocument.create("id"+i, "Приход"+i, new Date());
    }

    public static void addDummyIncomeDocument(){
        INCOME_DOCUMENTS.add(createDummyIncomeDocument(INCOME_DOCUMENTS.size()));
    }

    private static ShipmentTaskPosition createDummyShipment(int i) {
        return ShipmentTaskPosition.create(
                NomenclaturePosition.create("id"+i, "Название"+i,"Описание"+i, "art"+i,
                        ""+i+i+i, Image.EMPTY, DEFAULT_UNIT_LIST),
                i , 0
        );
    }

    private static List<Unit> getDefaultList() {
        List<Unit> result = new ArrayList<>();
        result.add(builder().id("1").name("шт.").build());
        result.add(builder().id("2").name("уп.").build());
        return result;
    }

    private static void createEquipmentTask(){
        List<ShipmentTaskPosition> dummyPositions = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            dummyPositions.add(createDummyShipment(i));
        }
        EQUIPMENT_TASK.add(Kit.create(new ArrayList<>(dummyPositions.subList(0,4)),
                NomenclaturePosition.create("012", "Шашлык", "набор для шашлыка", "шаш1", "123",
                        Image.EMPTY, DEFAULT_UNIT_LIST)));
        EQUIPMENT_TASK.add(Kit.create(new ArrayList<>(dummyPositions.subList(3,5)),
                NomenclaturePosition.create("012", "Праздник", "набор для праздника", "праз1", "321",
                        Image.EMPTY, DEFAULT_UNIT_LIST)));
    }



    private static void addDocument(ShipmentDocument item) {
        SHIPMENT_DOCUMENTS.add(item);
    }

    private static ShipmentDocument createDummyShipmentDocument(int i, Client client) {
        return ShipmentDocument.builder().id("AB" + i).client(client).docNumber(""+i)
                .comment("")
                .number("N"+i)
                .docDate(new Date()).build();
    }

    public static void addDummyShipmentDocument(){
        SHIPMENT_DOCUMENTS.add(createDummyShipmentDocument(SHIPMENT_DOCUMENTS.size(),
                CLIENTS.get(0)));
    }


    private static void addNomenclature(NomenclaturePosition item) {
        NOMENCLATURE.add(item);
        NOMENCLATURE_MAP.put(item.barCode(), item);
    }

    private static NomenclaturePosition createNomenclature(int i) {
        return NomenclaturePosition.builder().id(""+i).positionName("Товар "+i)
                .description("Описание товара "+i).vendorCode("AB"+i+"CD").barCode(""+i)
                .units(Dummy.DEFAULT_UNIT_LIST).image(Image.EMPTY).build();
    }
}
