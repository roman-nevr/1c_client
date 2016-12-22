package com.example.dmitry.a1c_client.domain.entity;

import com.google.auto.value.AutoValue;

import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.TransmitionState.NaS;
import static com.example.dmitry.a1c_client.domain.entity.IncomeTaskState.ViewState.barCodeInput;

/**
 * Created by roma on 18.12.2016.
 */

@AutoValue
public abstract class IncomeTaskState {

    public static final IncomeTaskState EMPTY = create(NomenclaturePosition.EMPTY, 0, Unit.EMPTY, "", "", barCodeInput, NaS, NaS, ok, StoreMapObject.EMPTY);

    public static Builder builder() {
        return new AutoValue_IncomeTaskState.Builder();
    }

    public static IncomeTaskState create(NomenclaturePosition position, int quantity, Unit unit, String storagePlace, String storageElement, ViewState viewState, TransmitionState positionState, TransmitionState storageState, ErrorState errorState, StoreMapObject storeMapObject) {
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

    public abstract TransmitionState positionState();

    public abstract TransmitionState storageState();

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

    public enum TransmitionState {
        NaS, requested, received, notFound, error  //Not a State
    }

    public enum ErrorState {
        ok, noRights, connectionError
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder position(NomenclaturePosition position);

        public abstract Builder quantity(int quantity);

        public abstract Builder unit(Unit unit);

        public abstract Builder storagePlace(String storagePlace);

        public abstract Builder storageElement(String storageElement);

        public abstract Builder viewState(ViewState viewState);

        public abstract Builder storageState(TransmitionState storageState);

        public abstract Builder positionState(TransmitionState positionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract Builder storeMapObject(StoreMapObject storeMapObject);

        public abstract IncomeTaskState build();
    }
}
