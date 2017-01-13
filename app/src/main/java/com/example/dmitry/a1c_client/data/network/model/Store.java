package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 13.01.2017.
 */

public class Store {
    @SerializedName("Ref_Key")
    private String refKey;
    @SerializedName("IsFolder")
    private boolean folder;
    @SerializedName("DeletionMark")
    private boolean deleted;
    @SerializedName("Code")
    private String code;
    @SerializedName("Description")
    private String name;

    public Store(String refKey, boolean folder, boolean deleted, String code, String name) {
        this.refKey = refKey;
        this.folder = folder;
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

    public boolean isFolder() {
        return folder;
    }

    public void setFolder(boolean folder) {
        this.folder = folder;
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
"Ref_Key": "b1c89d9c-cb6a-11e6-aede-5404a6b4b8ed",
"DataVersion": "AAAABwAAAAA=",
"DeletionMark": false,
"Parent_Key": "00000000-0000-0000-0000-000000000000",
"IsFolder": false,
"Code": "000000002",
"Description": "НеОсновной",
"ИдентификаторСклада": 3,
"Predefined": false,
"PredefinedDataName": ""
}
 */
