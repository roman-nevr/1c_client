package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class Image {
    public static final Image EMPTY = builder().id("").image("").build();

    public abstract String id();

    public abstract String image();

    public static Builder builder() {
        return new AutoValue_Image.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder image(String image);

        public abstract Image build();
    }
}
