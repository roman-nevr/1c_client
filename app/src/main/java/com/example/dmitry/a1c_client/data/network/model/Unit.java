package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 30.12.2016.
 */

public class Unit {
    @SerializedName("Ref_Key") private String ref;
    @SerializedName("DeletionMark") private boolean deletionMark;
    @SerializedName("Description") private String name;
}
