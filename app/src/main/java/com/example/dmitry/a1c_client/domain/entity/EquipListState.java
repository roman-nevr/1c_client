package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.notInitialased;

/**
 * Created by Admin on 29.12.2016.
 */

@AutoValue
public abstract class EquipListState {

    public static EquipListState EMPTY = builder()
            .documents(Collections.unmodifiableList(Collections.emptyList()))
            .state(notInitialased).build();

    public static EquipListState create(List<EquipDocument> documents, DocumentsState state) {
        return builder()
                .documents(documents)
                .state(state)
                .build();
    }

    public static Builder builder() {return new AutoValue_EquipListState.Builder();}

    abstract public Builder toBuilder();

    abstract public List<EquipDocument> documents();

    abstract public DocumentsState state();

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder documents(List<EquipDocument> documents);

        public abstract Builder state(DocumentsState state);

        public abstract EquipListState build();
    }
}
