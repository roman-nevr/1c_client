package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState;
import com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState;
import com.example.dmitry.a1c_client.presentation.entity.BarCodeEntryMap;
import com.google.auto.value.AutoValue;

import java.util.Collections;
import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.Enums.*;
import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.collect;
import static com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState.Stage.notInitialised;

/**
 * Created by Admin on 26.12.2016.
 */

@AutoValue
public abstract class EquipmentTaskState extends Shipable {

    private static List<Kit> KIT_EMPTY_LIST = Collections
            .unmodifiableList(Collections.emptyList());

    private static List<ShipmentTaskPosition> EMPTY_LIST = Collections
            .unmodifiableList(Collections.emptyList());

    public static EquipmentTaskState EMPTY = create(KIT_EMPTY_LIST, Kit.EMPTY, EMPTY_LIST,
            notInitailased, notInitialised, BarCodeEntryMap.EMPTY, actual, idle, ok);

    public static EquipmentTaskState create(List<Kit> kits,
                                            Kit kitToShow,
                                            List<ShipmentTaskPosition> positions,
                                            CompleteState completeState,
                                            Stage stage,
                                            BarCodeEntryMap barCodeEntryMap,
                                            DisplayState displayState,
                                            TransmissionState transmissionState,
                                            ErrorState errorState) {
        return builder()
                .kits(kits)
                .kitToShow(kitToShow)
                .positions(positions)
                .completeState(completeState)
                .stage(stage)
                .barCodeEntryMap(barCodeEntryMap)
                .whatToShow(displayState)
                .transmissionState(transmissionState)
                .errorState(errorState)
                .build();
    }

    public static Builder builder() {return new AutoValue_EquipmentTaskState.Builder();}

    public abstract List<Kit> kits();

    public abstract Kit kitToShow();

    public abstract BarCodeEntryMap barCodeEntryMap();

    public abstract Stage stage();

    public abstract Builder toBuilder();

    public enum Stage {
        notInitialised, collect, equip
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder kits(List<Kit> kits);

        public abstract Builder kitToShow(Kit kit);

        public abstract Builder positions(List<ShipmentTaskPosition> positions);

        public abstract Builder completeState(CompleteState completeState);

        public abstract Builder stage(Stage stage);

        public abstract Builder barCodeEntryMap(BarCodeEntryMap barCodeEntryMap);

        public abstract Builder whatToShow(DisplayState displayState);

        public abstract Builder transmissionState(TransmissionState transmissionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract EquipmentTaskState build();
    }
}
