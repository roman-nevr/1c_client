package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.*;

@AutoValue
abstract public class ShipmentListState {
    public static final ShipmentListState EMPTY = builder()
            .documents(Collections.unmodifiableList(Collections.emptyList()))
            .state(notInitialased).build();

    abstract public List<ShipmentDocument> documents();

    abstract public DocumentsState state();

    abstract public Builder toBuilder();

    public static Builder builder() {return new AutoValue_ShipmentListState.Builder();}

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder documents(List<ShipmentDocument> documents);

        public abstract Builder state(DocumentsState state);

        public abstract ShipmentListState build();
    }
}
