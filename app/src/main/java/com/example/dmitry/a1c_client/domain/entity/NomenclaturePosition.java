package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class NomenclaturePosition {

    public static final NomenclaturePosition EMPTY = builder().id("").positionName("")
            .description("").barCode("").vendorCode("").image(Image.EMPTY).units(Unit.EMPTY_LIST)
            .build();

    public abstract String id();

    public abstract String positionName();

    public abstract String description();

    public abstract String vendorCode();

    public abstract String barCode();

    public abstract Image image();

    public abstract List<Unit> units();

    public static NomenclaturePosition create(String id, String positionName, String description, String vendorCode, String barCode, Image image, List<Unit> units) {
        return builder()
                .id(id)
                .positionName(positionName)
                .description(description)
                .vendorCode(vendorCode)
                .barCode(barCode)
                .image(image)
                .units(units)
                .build();
    }


    public static Builder builder() {
        return new AutoValue_NomenclaturePosition.Builder();
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder description(String description);

        public abstract Builder id(String id);

        public abstract Builder image(Image image);

        public abstract Builder positionName(String positionName);

        public abstract Builder units(List<Unit> units);

        public abstract Builder vendorCode(String vendorCode);

        public abstract Builder barCode(String barCode);

        public abstract NomenclaturePosition build();
    }
}
