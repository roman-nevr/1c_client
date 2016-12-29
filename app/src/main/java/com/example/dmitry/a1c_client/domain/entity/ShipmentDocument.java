package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
abstract public class ShipmentDocument {
    abstract public String id();

    abstract public String docNumber();

    abstract public Date docDate();

    abstract public Client client();

    abstract public String number();

    abstract public String comment();

    public static ShipmentDocument create(String id, String docNumber, Date docDate, Client client, String number, String comment) {
        return builder()
                .id(id)
                .docNumber(docNumber)
                .docDate(docDate)
                .client(client)
                .number(number)
                .comment(comment)
                .build();
    }

    public static Builder builder() {return new AutoValue_ShipmentDocument.Builder();}

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder docNumber(String docNumber);

        public abstract Builder docDate(Date docDate);

        public abstract Builder client(Client client);

        abstract public Builder number(String number);

        public abstract Builder comment(String comment);

        public abstract ShipmentDocument build();
    }
}
