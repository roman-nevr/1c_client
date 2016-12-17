package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Client {
    abstract public String id();

    abstract public String name();

    public static Builder builder() {return new AutoValue_Client.Builder();}

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder name(String name);

        public abstract Client build();
    }
}
