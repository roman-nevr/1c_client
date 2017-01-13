package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 13.01.2017.
 */

public class EquipmentOrder {
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
    @SerializedName("Сборщик_Key")
    private String assemblerKey;
    @SerializedName("НомерРеализации")
    private String sellingNumber;
    @SerializedName("ДатаРеализации")
    private String sellingDate;
    @SerializedName("ДокументОснование_Key")
    private String baseDocumentKey;
    @SerializedName("Товары")
    private List<Good> goods;
}
/*
{
"Ref_Key": "8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAAAgAAAAA=",
"DeletionMark": false,
"Number": "000000001",
"Date": "2016-12-29T15:20:06",
"Posted": false,
"Сборщик_Key": "4b990e7f-c046-11e6-9160-5404a6b4b8ed",
"ВидОперации": "Комплектация",
"Комментарий": "",
"РабочийЦентр_Key": "f8c956f2-cdad-11e6-aede-5404a6b4b8ed",
"Подразделение_Key": "b837edfd-c36d-11e6-aede-5404a6b4b8ed",
"Склад_Key": "f8c956ee-cdad-11e6-aede-5404a6b4b8ed",
"ЗаданиеНаПроизводство_Key": "8bbfc2d1-cdb9-11e6-aede-5404a6b4b8ed",
"Комплектующие": [],
"Комплекты": [
{
"Ref_Key": "8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "f8c956ea-cdad-11e6-aede-5404a6b4b8ed",
"Количество": 5,
"ЕдиницаИзмерения_Key": "f8c956ec-cdad-11e6-aede-5404a6b4b8ed",
"Качество_Key": "c531161f-422a-4245-8f9a-569def45e0f4",
"ШтрихКод": "             ",
"МестоХранения_Key": "00000000-0000-0000-0000-000000000000",
"СпособТранспортировки_Key": "8bbfc2c5-cdb9-11e6-aede-5404a6b4b8ed",
"ЭлементСтруктурыХранения_Key": "00000000-0000-0000-0000-000000000000",
"Подобран": false,
"Спецификация_Key": "8bbfc2d2-cdb9-11e6-aede-5404a6b4b8ed",
"Кладовщик_Key": "00000000-0000-0000-0000-000000000000"
}
],
"Сборщик@navigationLinkUrl": "Document_Комплектация(guid'8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed')/Сборщик",
"РабочийЦентр@navigationLinkUrl": "Document_Комплектация(guid'8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed')/РабочийЦентр",
"Подразделение@navigationLinkUrl": "Document_Комплектация(guid'8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed')/Подразделение",
"Склад@navigationLinkUrl": "Document_Комплектация(guid'8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed')/Склад",
"ЗаданиеНаПроизводство@navigationLinkUrl": "Document_Комплектация(guid'8bbfc2d3-cdb9-11e6-aede-5404a6b4b8ed')/ЗаданиеНаПроизводство"
}
 */
