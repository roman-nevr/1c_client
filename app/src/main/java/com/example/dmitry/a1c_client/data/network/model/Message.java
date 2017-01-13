package com.example.dmitry.a1c_client.data.network.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by Admin on 12.01.2017.
 */
public class Message {
    @SerializedName("lang")
    private String lang;
    @SerializedName("value")
    private String message;

    public Message(String lang, String message) {
        this.lang = lang;
        this.message = message;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
