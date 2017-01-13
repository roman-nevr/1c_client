package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 13.01.2017.
 */

public class User {
    @SerializedName("Ref_Key")
    private String refKey;
    @SerializedName("DeletionMark")
    private boolean deleted;
    @SerializedName("Code")
    private String code;
    @SerializedName("Description")
    private String name;

    public User(String refKey, boolean deleted, String code, String name) {
        this.refKey = refKey;
        this.deleted = deleted;
        this.code = code;
        this.name = name;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

/*
{
"Ref_Key": "4b990e7f-c046-11e6-9160-5404a6b4b8ed",
"DataVersion": "AAAAAQAAAAA=",
"DeletionMark": false,
"Code": "Администратор",
"Description": "Администратор",
"ОсновныеНастройки": [],
"Predefined": false,
"PredefinedDataName": ""
}
 */
