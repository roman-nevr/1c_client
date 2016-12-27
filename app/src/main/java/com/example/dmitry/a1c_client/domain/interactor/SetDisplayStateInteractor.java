package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums.DisplayState;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import javax.inject.Inject;

/**
 * Created by Admin on 27.12.2016.
 */

public abstract class SetDisplayStateInteractor extends Interactor {

    protected DisplayState state;

    public Interactor setDisplayState(DisplayState state){
        this.state = state;
        return this;
    }


}
