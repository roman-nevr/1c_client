package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 13.01.2017.
 */
public class Good {
    @SerializedName("Ref_Key")
    private String refKey;
    @SerializedName("LineNumber")
    private int lineNumber;
    @SerializedName("Номенклатура_Key")
    private String nomenklaturaKey;
    @SerializedName("Количество")
    private int quantity;
    @SerializedName("ЕдиницаИзмерения_Key")
    private String unitKey;
    @SerializedName("Качество_Key")
    private String qualityKey;

    public Good(String refKey, int lineNumber, String nomenklaturaKey, int quantity,
                String unitKey, String qualityKey) {
        this.refKey = refKey;
        this.lineNumber = lineNumber;
        this.nomenklaturaKey = nomenklaturaKey;
        this.quantity = quantity;
        this.unitKey = unitKey;
        this.qualityKey = qualityKey;
    }

    public String getRefKey() {
        return refKey;
    }

    public void setRefKey(String refKey) {
        this.refKey = refKey;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    public String getNomenklaturaKey() {
        return nomenklaturaKey;
    }

    public void setNomenklaturaKey(String nomenklaturaKey) {
        this.nomenklaturaKey = nomenklaturaKey;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getUnitKey() {
        return unitKey;
    }

    public void setUnitKey(String unitKey) {
        this.unitKey = unitKey;
    }

    public String getQualityKey() {
        return qualityKey;
    }

    public void setQualityKey(String qualityKey) {
        this.qualityKey = qualityKey;
    }
}
/*
{
"Ref_Key": "8bbfc2c7-cdb9-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "f8c956ea-cdad-11e6-aede-5404a6b4b8ed",
"Количество": 5,
"ЕдиницаИзмерения_Key": "f8c956ec-cdad-11e6-aede-5404a6b4b8ed",
"Качество_Key": "7949e1e4-b69b-48d2-b767-9c3a0a2d9e93"
}
 */

/*
{
"Ref_Key": "f8c956e5-cdad-11e6-aede-5404a6b4b8ed",
"LineNumber": "1",
"Номенклатура_Key": "68c21ae9-cd73-11e6-aede-5404a6b4b8ed",
"Количество": 2,
"ЕдиницаИзмерения_Key": "136f8719-cd75-11e6-aede-5404a6b4b8ed",
"Качество_Key": "c531161f-422a-4245-8f9a-569def45e0f4",
"ШтрихКод": "1                   ",
"МестоХранения_Key": "00000000-0000-0000-0000-000000000000",
"СпособТранспортировки_Key": "b1c89da2-cb6a-11e6-aede-5404a6b4b8ed",
"ЭлементСтруктурыХранения_Key": "00000000-0000-0000-0000-000000000000",
"СерийныйНомер": "",
"Подобран": false,
"СпособПодбораМеста": "",
"Кладовщик_Key": "f8c956e4-cdad-11e6-aede-5404a6b4b8ed"
},
 */