package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.State.empty;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class IncomeTaskState {

    public static final IncomeTaskState EMPTY = builder().position(NomenclaturePosition.EMPTY)
            .quantity(0).state(empty).unit(Unit.EMPTY).storageElement("").storagePlace("")
            .storeMapObject(StoreMapObject.EMPTY).build();

    public abstract NomenclaturePosition position();

    public abstract int quantity();

    public abstract Unit unit();

    public abstract String storagePlace();

    public abstract String storageElement();

    public abstract State state();

    public abstract StoreMapObject storeMapObject();

    public static Builder builder() {
        return new AutoValue_IncomeTaskState.Builder();
    }

    public abstract Builder toBuilder();

    public enum State{
        empty, ready,
        progress, positionReceived, positionTransmissionError,
        storagePlaceReceived, storagePlaceTransmissionError,
        barCodeSavingTransmissionError, barCodeNotFoundDialog, newBarcodeDialog,
        barCodeSaving, noRightsDialog
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder position(NomenclaturePosition position);

        public abstract Builder quantity(int quantity);

        public abstract Builder unit(Unit unit);

        public abstract Builder storagePlace(String storagePlace);

        public abstract Builder storageElement(String storageElement);

        public abstract Builder state(State state);

        public abstract Builder storeMapObject(StoreMapObject storeMapObject);

        public abstract IncomeTaskState build();
    }
}
