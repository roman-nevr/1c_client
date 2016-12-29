package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Date;

/**
 * Created by Admin on 29.12.2016.
 */

@AutoValue
public abstract class IncomeDocument {
    abstract public String id();

    abstract public String docNumber();

    abstract public Date docDate();

    public static IncomeDocument create(String id, String docNumber, Date docDate) {
        return builder()
                .id(id)
                .docNumber(docNumber)
                .docDate(docDate)
                .build();
    }

    public static Builder builder() {return new AutoValue_IncomeDocument.Builder();}


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder docNumber(String docNumber);

        public abstract Builder docDate(Date docDate);

        public abstract IncomeDocument build();
    }
}
