package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState;
import com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState;
import com.google.auto.value.AutoValue;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.barCodeInput;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class IncomeTaskState {

    public static final IncomeTaskState EMPTY = create(NomenclaturePosition.EMPTY, 0, Unit.EMPTY, "", "", barCodeInput, idle, idle, ok, StoreMapObject.EMPTY);

    public static Builder builder() {
        return new AutoValue_IncomeTaskState.Builder();
    }

    public static IncomeTaskState create(NomenclaturePosition position, int quantity, Unit unit, String storagePlace, String storageElement, ViewState viewState, TransmissionState positionState, TransmissionState storageState, ErrorState errorState, StoreMapObject storeMapObject) {
        return builder()
                .position(position)
                .quantity(quantity)
                .unit(unit)
                .storagePlace(storagePlace)
                .storageElement(storageElement)
                .viewState(viewState)
                .positionState(positionState)
                .storageState(storageState)
                .errorState(errorState)
                .storeMapObject(storeMapObject)
                .build();
    }

    public abstract NomenclaturePosition position();

    public abstract int quantity();

    public abstract Unit unit();

    public abstract String storagePlace();

    public abstract String storageElement();

    public abstract ViewState viewState();

    public abstract TransmissionState positionState();

    public abstract TransmissionState storageState();

    public abstract ErrorState errorState();

    public abstract StoreMapObject storeMapObject();

    public abstract Builder toBuilder();


    public enum ViewState {
        barCodeInput,
        waitForBarCodeAnswer,
        displayPosition, newBarcodeDialog, //saving
        waitForStorageInfoAnswer,
        displayStorageInfo, //error
        barCodeSaving
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder position(NomenclaturePosition position);

        public abstract Builder quantity(int quantity);

        public abstract Builder unit(Unit unit);

        public abstract Builder storagePlace(String storagePlace);

        public abstract Builder storageElement(String storageElement);

        public abstract Builder viewState(ViewState viewState);

        public abstract Builder storageState(TransmissionState storageState);

        public abstract Builder positionState(TransmissionState positionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract Builder storeMapObject(StoreMapObject storeMapObject);

        public abstract IncomeTaskState build();
    }

}
