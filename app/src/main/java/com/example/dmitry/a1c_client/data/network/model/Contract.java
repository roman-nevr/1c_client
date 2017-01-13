package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 13.01.2017.
 */

public class Contract {
    @SerializedName("Ref_Key")
    private String refKey;
    @SerializedName("DeletionMark")
    private boolean deleted;
    @SerializedName("Posted")
    private boolean posted;
    @SerializedName("Date")
    private String date;
    @SerializedName("Склад_Key")
    private String storeKey;
    @SerializedName("Контрагент_Key")
    private String client;
    @SerializedName("Подразделение_Key")
    private String subdivisionKey;
    @SerializedName("Комментарий")
    private String comment;
    @SerializedName("Ответственный_Key")
    private String responsibleKey;
    @SerializedName("Товары")
    private List<Good> goods;
}
/*
{
"odata.metadata": "http://moon/Test/odata/standard.odata/$metadata#Document_Контракт",
"value": [{
"Ref_Key": "b1c89da7-cb6a-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAABAAAAAA=",
"DeletionMark": false,
"Number": "00000000001",
"Date": "2016-12-26T17:08:57",
"Posted": true,
"Склад_Key": "3aa3cd58-c109-11e6-a7a6-5404a6b4b8ed",
"Контрагент_Key": "b1c89d93-cb6a-11e6-aede-5404a6b4b8ed",
"Комментарий": "",
"Подразделение_Key": "b837edfd-c36d-11e6-aede-5404a6b4b8ed",
"Ответственный_Key": "b1c89da6-cb6a-11e6-aede-5404a6b4b8ed",
"Товары": [],
"Склад@navigationLinkUrl": "Document_Контракт(guid'b1c89da7-cb6a-11e6-aede-5404a6b4b8ed')/Склад",
"Контрагент@navigationLinkUrl": "Document_Контракт(guid'b1c89da7-cb6a-11e6-aede-5404a6b4b8ed')/Контрагент",
"Подразделение@navigationLinkUrl": "Document_Контракт(guid'b1c89da7-cb6a-11e6-aede-5404a6b4b8ed')/Подразделение",
"Ответственный@navigationLinkUrl": "Document_Контракт(guid'b1c89da7-cb6a-11e6-aede-5404a6b4b8ed')/Ответственный"
},{
"Ref_Key": "8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAABgAAAAA=",
"DeletionMark": false,
"Number": "00000000002",
"Date": "2016-12-29T14:35:27",
"Posted": true,
"Склад_Key": "f8c956ee-cdad-11e6-aede-5404a6b4b8ed",
"Контрагент_Key": "b1c89d93-cb6a-11e6-aede-5404a6b4b8ed",
"Комментарий": "",
"Подразделение_Key": "b837edfd-c36d-11e6-aede-5404a6b4b8ed",
"Ответственный_Key": "b1c89da6-cb6a-11e6-aede-5404a6b4b8ed",
"Товары": [
{
"Ref_Key": "8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "f8c956ea-cdad-11e6-aede-5404a6b4b8ed",
"Количество": 5,
"ЕдиницаИзмерения_Key": "f8c956ec-cdad-11e6-aede-5404a6b4b8ed",
"Качество_Key": "7949e1e4-b69b-48d2-b767-9c3a0a2d9e93"
},
{
"Ref_Key": "8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "2",
"Номенклатура_Key": "68c21ae9-cd73-11e6-aede-5404a6b4b8ed",
"Количество": 31,
"ЕдиницаИзмерения_Key": "68c21aea-cd73-11e6-aede-5404a6b4b8ed",
"Качество_Key": "306b4034-e07f-4328-ae5a-70dde4741049"
}
],
"Склад@navigationLinkUrl": "Document_Контракт(guid'8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed')/Склад",
"Контрагент@navigationLinkUrl": "Document_Контракт(guid'8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed')/Контрагент",
"Подразделение@navigationLinkUrl": "Document_Контракт(guid'8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed')/Подразделение",
"Ответственный@navigationLinkUrl": "Document_Контракт(guid'8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed')/Ответственный"
}]
}
 */