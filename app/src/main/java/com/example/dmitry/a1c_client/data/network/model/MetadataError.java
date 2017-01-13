package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 12.01.2017.
 */

public class MetadataError {
    @SerializedName("odata.error")
    private OdataError error;

    public MetadataError(OdataError error) {
        this.error = error;
    }

    public OdataError getError() {
        return error;
    }

    public void setError(OdataError error) {
        this.error = error;
    }
}
