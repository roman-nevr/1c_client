package com.example.dmitry.a1c_client.domain.entity;

/**
 * Created by Admin on 23.12.2016.
 */

public class Enums {
    public static enum TransmissionState {
        idle, requested, received, notFound, error  //Not a State
    }

    public static enum ErrorState {
        ok, noRights, connectionError
    }

    public enum CompleteState {
        notInitailased, notComplete, comlete
    }

    public enum DisplayState {
        all, actual
    }
}
