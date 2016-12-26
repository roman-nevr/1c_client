package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition.EMPTY_LIST;

/**
 * Created by Admin on 26.12.2016.
 */

@AutoValue
public abstract class Kit{

    public static Kit EMPTY = create(EMPTY_LIST, NomenclaturePosition.EMPTY);

    public abstract List<ShipmentTaskPosition> kitContent();

    public abstract NomenclaturePosition kitName();

    public abstract Builder toBuilder();

    public static Kit create(List<ShipmentTaskPosition> kitContent, NomenclaturePosition kitName) {
        return builder()
                .kitContent(kitContent)
                .kitName(kitName)
                .build();
    }

    public static Builder builder() {return new AutoValue_Kit.Builder();}


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder kitContent(List<ShipmentTaskPosition> kitContent);

        public abstract Builder kitName(NomenclaturePosition kitName);

        public abstract Kit build();
    }
}
