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

/**
 * Created by Admin on 26.12.2016.
 */

@AutoValue
public abstract class EquipmentTaskState {

    private static List<Kit> EMPTY_LIST = Collections.unmodifiableList(Collections.emptyList());

    public static EquipmentTaskState EMPTY = create(EMPTY_LIST, Kit.EMPTY, notInitailased,
            collect, BarCodeEntryMap.EMPTY, actual, idle, ok);

    public abstract List<Kit> kits();

    public abstract Kit kitToShow();

    public abstract BarCodeEntryMap barCodeEntryMap();

    public abstract CompleteState completeState();

    public abstract Stage stage();

    public abstract DisplayState whatToShow();

    public abstract TransmissionState transmissionState();

    public abstract ErrorState errorState();

    public abstract Builder toBuilder();

    public static EquipmentTaskState create(List<Kit> kits,
                                            Kit kitToShow,
                                            CompleteState completeState,
                                            Stage stage,
                                            BarCodeEntryMap barCodeEntryMap,
                                            DisplayState displayState,
                                            TransmissionState transmissionState,
                                            ErrorState errorState)
    {
        return builder()
                .kits(kits)
                .kitToShow(kitToShow)
                .completeState(completeState)
                .stage(stage)
                .barCodeEntryMap(barCodeEntryMap)
                .whatToShow(displayState)
                .transmissionState(transmissionState)
                .errorState(errorState)
                .build();
    }

    public enum Stage{
        collect, equip
    }

    public static Builder builder() {return new AutoValue_EquipmentTaskState.Builder();}

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder kits(List<Kit> kits);

        public abstract Builder kitToShow(Kit kit);

        public abstract Builder completeState(CompleteState completeState);

        public abstract Builder stage(Stage stage);

        public abstract Builder barCodeEntryMap(BarCodeEntryMap barCodeEntryMap);

        public abstract Builder whatToShow(DisplayState displayState);

        public abstract Builder transmissionState(TransmissionState transmissionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract EquipmentTaskState build();
    }
}
