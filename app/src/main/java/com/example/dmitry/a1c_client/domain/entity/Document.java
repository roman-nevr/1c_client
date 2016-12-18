package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Date;

@AutoValue
abstract public class Document {
    abstract public String id();

    abstract public String docNumber();

    abstract public Date docDate();

    abstract public Client client();

    abstract public String comment();

    public static Builder builder() {return new AutoValue_Document.Builder();}


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder docNumber(String docNumber);

        public abstract Builder docDate(Date docDate);

        public abstract Builder client(Client client);

        public abstract Builder comment(String comment);

        public abstract Document build();
    }
}
