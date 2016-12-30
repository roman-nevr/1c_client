package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;
import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by Admin on 29.12.2016.
 */

@AutoValue
public abstract class EquipDocument {
    abstract public String id();

    abstract public String docNumber();

    abstract public Date docDate();

    abstract public Client client();

    abstract public String comment();

    public static EquipDocument create(String id, String docNumber, Date docDate, Client client, String comment) {
        return builder()
                .id(id)
                .docNumber(docNumber)
                .docDate(docDate)
                .client(client)
                .comment(comment)
                .build();
    }

    public static Builder builder() {return new AutoValue_EquipDocument.Builder();}


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder docNumber(String docNumber);

        public abstract Builder docDate(Date docDate);

        public abstract Builder client(Client client);

        public abstract Builder comment(String comment);

        public abstract EquipDocument build();
    }
}
