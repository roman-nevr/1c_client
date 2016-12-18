package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class Unit {
    public static final Unit EMPTY = builder().id("").name("").build();

    public static final List<Unit> EMPTY_LIST =
            Collections.unmodifiableList(Collections.emptyList());

    public abstract String id();

    public abstract String name();

    public static Builder builder() {
        return new AutoValue_Unit.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder name(String name);

        public abstract Unit build();
    }


}
