package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState;
import com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState;
import com.google.auto.value.AutoValue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState.CompleteState.notComplete;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState.DisplayState.actual;

/**
 * Created by Admin on 23.12.2016.
 */

@AutoValue
public abstract class ShipmentTaskState {

    private static List<ShipmentTaskPosition> EMPTY_LIST =
            Collections.unmodifiableList(new ArrayList<>());

    public static ShipmentTaskState EMPTY = create(EMPTY_LIST, EMPTY_LIST, actual, notComplete, idle, ok);



    public static Builder builder() {return new AutoValue_ShipmentTaskState.Builder();}



    public abstract List<ShipmentTaskPosition> initialPositions();

    public abstract List<ShipmentTaskPosition> actualPositions();

    public abstract DisplayState whatToShow();

    public abstract CompleteState completeState();

    public abstract TransmissionState transmissionState();

    public abstract ErrorState errorState();

    public abstract Builder toBuilder();

    public static ShipmentTaskState create(List<ShipmentTaskPosition> initialPositions, List<ShipmentTaskPosition> actualPositions, DisplayState whatToShow, CompleteState completeState, TransmissionState transmissionState, ErrorState errorState) {
        return builder()
                .initialPositions(initialPositions)
                .actualPositions(actualPositions)
                .whatToShow(whatToShow)
                .completeState(completeState)
                .transmissionState(transmissionState)
                .errorState(errorState)
                .build();
    }


    public enum DisplayState {
        all, actual
    }

    public enum CompleteState {
        notComplete, comlete
    }


    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder initialPositions(List<ShipmentTaskPosition> initialPositions);

        public abstract Builder actualPositions(List<ShipmentTaskPosition> actualPositions);

        public abstract Builder whatToShow(DisplayState whatToShow);

        public abstract Builder completeState(CompleteState completeState);

        public abstract Builder transmissionState(TransmissionState transmissionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract ShipmentTaskState build();
    }
}
