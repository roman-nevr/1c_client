package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Admin on 12.01.2017.
 */

public class Odata<T> {
    @SerializedName("odata.metadata")
    private String odata;
    @SerializedName("value")
    private List<T> values;

    public Odata(String odata, List<T> values) {
        this.odata = odata;
        this.values = values;
    }

    public String getOdata() {
        return odata;
    }

    public void setOdata(String odata) {
        this.odata = odata;
    }

    public List<T> getValues() {
        return values;
    }

    public void setValues(List<T> values) {
        this.values = values;
    }
}
