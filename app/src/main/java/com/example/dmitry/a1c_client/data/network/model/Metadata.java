package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

public class Metadata {

    @SerializedName("name")
    private String name;
    @SerializedName("url")
    private String url;

    public Metadata(String name, String url) {
        super();
        this.name = name;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}