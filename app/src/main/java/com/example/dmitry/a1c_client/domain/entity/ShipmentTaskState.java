package com.example.dmitry.a1c_client.domain.entity;

import com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState;
import com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState;
import com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState;
import com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState;
import com.google.auto.value.AutoValue;

import java.util.List;

import static com.example.dmitry.a1c_client.domain.entity.Enums.CompleteState.notInitailased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState.actual;
import static com.example.dmitry.a1c_client.domain.entity.Enums.ErrorState.ok;
import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.idle;
import static com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition.EMPTY_LIST;

/**
 * Created by Admin on 23.12.2016.
 */

@AutoValue
public abstract class ShipmentTaskState {

    public static ShipmentTaskState EMPTY = create(EMPTY_LIST, notInitailased, actual, idle, ok);

    public static Builder builder() {return new AutoValue_ShipmentTaskState.Builder();}

    public abstract List<ShipmentTaskPosition> positions();

    public abstract CompleteState completeState();

    public abstract DisplayState whatToShow();

    public abstract TransmissionState transmissionState();

    public abstract ErrorState errorState();

    public abstract Builder toBuilder();

    public static ShipmentTaskState create(List<ShipmentTaskPosition> positions, CompleteState completeState, DisplayState displayState, TransmissionState transmissionState, ErrorState errorState) {
        return builder()
                .positions(positions)
                .completeState(completeState)
                .whatToShow(displayState)
                .transmissionState(transmissionState)
                .errorState(errorState)
                .build();
    }

    @AutoValue.Builder public abstract static class Builder {
        public abstract Builder positions(List<ShipmentTaskPosition> initialPositions);

        public abstract Builder completeState(CompleteState completeState);

        public abstract Builder whatToShow(DisplayState displayState);

        public abstract Builder transmissionState(TransmissionState transmissionState);

        public abstract Builder errorState(ErrorState errorState);

        public abstract ShipmentTaskState build();
    }
}
