package com.example.dmitry.a1c_client.domain.entity;

import android.os.Bundle;

import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Admin on 23.12.2016.
 */

@AutoValue
public abstract class ShipmentTaskPosition {

    public static ShipmentTaskPosition EMPTY = ShipmentTaskPosition
            .create(NomenclaturePosition.EMPTY, 0, 0);

    public static List<ShipmentTaskPosition> EMPTY_LIST =
            Collections.unmodifiableList(new ArrayList<>());

    public static ShipmentTaskPosition create(NomenclaturePosition position, int requiredQuantity, int doneQuantity) {
        return builder()
                .position(position)
                .requiredQuantity(requiredQuantity)
                .doneQuantity(doneQuantity)
                .build();
    }

    public static Builder builder() {return new AutoValue_ShipmentTaskPosition.Builder();}

    public abstract NomenclaturePosition position();

    public abstract int requiredQuantity();

    public abstract int doneQuantity();

    public abstract Builder toBulder();

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder position(NomenclaturePosition position);

        public abstract Builder requiredQuantity(int requiredQuantity);

        public abstract Builder doneQuantity(int doneQuantity);

        public abstract ShipmentTaskPosition build();
    }
}
