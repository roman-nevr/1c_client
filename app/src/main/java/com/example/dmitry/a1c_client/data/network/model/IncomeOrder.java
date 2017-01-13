package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 13.01.2017.
 */

public class IncomeOrder {
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
    @SerializedName("Контрагент_Key")
    private String clientKey;
    @SerializedName("НомерКонтракта")
    private String contractNumber;
    @SerializedName("ДатаКонтракта")
    private String contractDate;
    @SerializedName("ДокументОснование_Key")
    private String baseDocumentKey;
    @SerializedName("Товары")
    private List<Good> goods;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public IncomeOrder(String refKey, boolean deleted, boolean posted, String date, String number, String operationType, String clientKey, String contractNumber, String contractDate, String baseDocumentKey, List<Good> goods) {

        this.refKey = refKey;
        this.deleted = deleted;
        this.posted = posted;
        this.date = date;
        this.number = number;
        this.operationType = operationType;
        this.clientKey = clientKey;
        this.contractNumber = contractNumber;
        this.contractDate = contractDate;
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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractDate() {
        return contractDate;
    }

    public void setContractDate(String contractDate) {
        this.contractDate = contractDate;
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
"Ref_Key": "b1c89d94-cb6a-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAAAQAAAAA=",
"DeletionMark": false,
"Number": "000000001",
"Date": "2016-12-26T16:00:52",
"Posted": false,
"ВидОперации": "Покупка",
"Склад_Key": "3aa3cd58-c109-11e6-a7a6-5404a6b4b8ed",
"Контрагент_Key": "b1c89d93-cb6a-11e6-aede-5404a6b4b8ed",
"НомерКонтракта": "           ",
"ДатаКонтракта": "0001-01-01T00:00:00",
"Комментарий": "",
"Подразделение_Key": "b837edfd-c36d-11e6-aede-5404a6b4b8ed",
"РабочийЦентр_Key": "b837edff-c36d-11e6-aede-5404a6b4b8ed",
"Ответственный_Key": "4b990e7f-c046-11e6-9160-5404a6b4b8ed",
"ОкончаниеОприходованиеКонтракта": false,
"ДокументОснование_Key": "00000000-0000-0000-0000-000000000000",
"ПриходоватьТоварПоСериям": false,
"Товары": [
{
"Ref_Key": "b1c89d94-cb6a-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "00000000-0000-0000-0000-000000000000",
"Количество": 0,
"ЕдиницаИзмерения_Key": "00000000-0000-0000-0000-000000000000",
"Качество_Key": "00000000-0000-0000-0000-000000000000",
"ШтрихКод": "                    ",
"МестоХранения_Key": "00000000-0000-0000-0000-000000000000",
"СпособТранспортировки_Key": "00000000-0000-0000-0000-000000000000",
"ЭлементСтруктурыХранения_Key": "00000000-0000-0000-0000-000000000000",
"СерийныйНомер": "",
"Подобран": false,
"СпособПодбораМеста": "",
"Кладовщик_Key": "00000000-0000-0000-0000-000000000000"
}
],
"Излишки": [],
"Недостача": [],
"Контроль": [],
"Стеллажи": [],
"Склад@navigationLinkUrl": "Document_ПриходныйОрдер(guid'b1c89d94-cb6a-11e6-aede-5404a6b4b8ed')/Склад",
"Контрагент@navigationLinkUrl": "Document_ПриходныйОрдер(guid'b1c89d94-cb6a-11e6-aede-5404a6b4b8ed')/Контрагент",
"Подразделение@navigationLinkUrl": "Document_ПриходныйОрдер(guid'b1c89d94-cb6a-11e6-aede-5404a6b4b8ed')/Подразделение",
"РабочийЦентр@navigationLinkUrl": "Document_ПриходныйОрдер(guid'b1c89d94-cb6a-11e6-aede-5404a6b4b8ed')/РабочийЦентр",
"Ответственный@navigationLinkUrl": "Document_ПриходныйОрдер(guid'b1c89d94-cb6a-11e6-aede-5404a6b4b8ed')/Ответственный"
}
 */