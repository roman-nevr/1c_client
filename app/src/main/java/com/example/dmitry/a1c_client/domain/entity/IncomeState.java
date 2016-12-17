package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

@AutoValue
abstract public class IncomeState {
    public static final IncomeState EMPTY = builder()
            .documents(Collections.unmodifiableList(Collections.emptyList()))
            .state(State.notInitialased).build();

    abstract public List<Document> documents();

    abstract public State state();

    abstract public Builder toBuilder();

    public static Builder builder() {return new AutoValue_IncomeState.Builder();}

    public enum State {
        error, ready, progress, notInitialased
    }


    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder documents(List<Document> documents);

        public abstract Builder state(State state);

        public abstract IncomeState build();
    }
}
