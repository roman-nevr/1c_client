package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 30.12.2016.
 */

public class ShipmentOrder {
    @SerializedName("Ref_Key")
    private String refKey;
    @SerializedName("DeletionMark")
    private boolean deleted;
    @SerializedName("Posted")
    private boolean posted;
    @SerializedName("Date")
    private String date;
    @SerializedName("Number")
    private String number;
    @SerializedName("ВидОперации")
    private String operationType;
    @SerializedName("Склад_Key")
    private String storeKey;
    @SerializedName("НомерРеализации")
    private String sellingNumber;
    @SerializedName("ДатаРеализации")
    private String sellingDate;
    @SerializedName("ДокументОснование_Key")
    private String baseDocumentKey;
    @SerializedName("Товары")
    private List<Good> goods;

    public ShipmentOrder(String refKey, boolean deleted, boolean posted, String date, String number, String operationType, String storeKey, String sellingNumber, String sellingDate, String baseDocumentKey, List<Good> goods) {
        this.refKey = refKey;
        this.deleted = deleted;
        this.posted = posted;
        this.date = date;
        this.number = number;
        this.operationType = operationType;
        this.storeKey = storeKey;
        this.sellingNumber = sellingNumber;
        this.sellingDate = sellingDate;
        this.baseDocumentKey = baseDocumentKey;
        this.goods = goods;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    public boolean isPosted() {
        return posted;
    }

    public void setPosted(boolean posted) {
        this.posted = posted;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getStoreKey() {
        return storeKey;
    }

    public void setStoreKey(String storeKey) {
        this.storeKey = storeKey;
    }

    public String getSellingNumber() {
        return sellingNumber;
    }

    public void setSellingNumber(String sellingNumber) {
        this.sellingNumber = sellingNumber;
    }

    public String getSellingDate() {
        return sellingDate;
    }

    public void setSellingDate(String sellingDate) {
        this.sellingDate = sellingDate;
    }

    public String getBaseDocumentKey() {
        return baseDocumentKey;
    }

    public void setBaseDocumentKey(String baseDocumentKey) {
        this.baseDocumentKey = baseDocumentKey;
    }

    public List<Good> getGoods() {
        return goods;
    }

    public void setGoods(List<Good> goods) {
        this.goods = goods;
    }
}
/*
{
"Ref_Key": "8bbfc2cf-cdb9-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAACwAAAAA=",
"DeletionMark": false,
"Number": "000000002",
"Date": "2016-12-29T15:13:29",
"Posted": false,
"Склад_Key": "f8c956ee-cdad-11e6-aede-5404a6b4b8ed",
"Контрагент": "f8c956ee-cdad-11e6-aede-5404a6b4b8ed",
"Контрагент_Type": "StandardODATA.Catalog_Склады",
"НомерРеализации": "           ",
"ДатаРеализации": "0001-01-01T00:00:00",
"Комментарий": "",
"Подразделение_Key": "b837edfd-c36d-11e6-aede-5404a6b4b8ed",
"Ответственный_Key": "00000000-0000-0000-0000-000000000000",
"ДокументОснование_Key": "00000000-0000-0000-0000-000000000000",
"ВидОперации": "",
"ОтгружатьТоварПоСериям": false,
"Товары": [
{
"Ref_Key": "8bbfc2cf-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "f8c956ea-cdad-11e6-aede-5404a6b4b8ed",
"Количество": 1,
"ЕдиницаИзмерения_Key": "f8c956ec-cdad-11e6-aede-5404a6b4b8ed",
"Качество_Key": "c531161f-422a-4245-8f9a-569def45e0f4",
"ШтрихКод": "                    ",
"МестоХранения_Key": "00000000-0000-0000-0000-000000000000",
"СпособТранспортировки_Key": "b1c89da1-cb6a-11e6-aede-5404a6b4b8ed",
"ЭлементСтруктурыХранения_Key": "00000000-0000-0000-0000-000000000000",
"Подобран": false,
"СерийныйНомер": "",
"Кладовщик_Key": "00000000-0000-0000-0000-000000000000"
},
{
"Ref_Key": "8bbfc2cf-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "2",
"Номенклатура_Key": "f8c956ea-cdad-11e6-aede-5404a6b4b8ed",
"Количество": 2,
"ЕдиницаИзмерения_Key": "f8c956ec-cdad-11e6-aede-5404a6b4b8ed",
"Качество_Key": "c531161f-422a-4245-8f9a-569def45e0f4",
"ШтрихКод": "                    ",
"МестоХранения_Key": "00000000-0000-0000-0000-000000000000",
"СпособТранспортировки_Key": "8bbfc2c5-cdb9-11e6-aede-5404a6b4b8ed",
"ЭлементСтруктурыХранения_Key": "00000000-0000-0000-0000-000000000000",
"Подобран": false,
"СерийныйНомер": "234",
"Кладовщик_Key": "00000000-0000-0000-0000-000000000000"
}
],
"Склад@navigationLinkUrl": "Document_РасходныйОрдер(guid'8bbfc2cf-cdb9-11e6-aede-5404a6b4b8ed')/Склад",
"Подразделение@navigationLinkUrl": "Document_РасходныйОрдер(guid'8bbfc2cf-cdb9-11e6-aede-5404a6b4b8ed')/Подразделение"
}
 */