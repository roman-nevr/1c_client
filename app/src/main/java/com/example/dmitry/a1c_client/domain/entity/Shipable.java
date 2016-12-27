package com.example.dmitry.a1c_client.domain.entity;

import java.util.List;

/**
 * Created by Admin on 27.12.2016.
 */

public abstract class Shipable {

    public abstract List<ShipmentTaskPosition> positions();

    public abstract Enums.CompleteState completeState();

    public abstract Enums.DisplayState whatToShow();

    public abstract Enums.TransmissionState transmissionState();

    public abstract Enums.ErrorState errorState();
}
