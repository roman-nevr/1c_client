package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.Shipable;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;

/**
 * Created by Admin on 27.12.2016.
 */

public abstract class ChangePositionInteractor extends Interactor {

    private String id;
    private int newQuantity;


    public Interactor setData(String id, int newQuantity) {
        this.id = id;
        this.newQuantity = newQuantity;
        return this;
    }

    @Override
    protected void operation() {
        //need to update particular ShipmentPosition in list
        checkInitArgs();
        ShipmentTaskPosition newPosition = findPositionById();
        List<ShipmentTaskPosition> positions = new ArrayList<>(getValue().positions());
        int index = positions.indexOf(newPosition);
        newPosition = changeQuantity(newPosition);
        positions.set(index, newPosition);
        updateState(positions);
    }

    protected abstract Shipable getValue();

    protected abstract void updateState(List<ShipmentTaskPosition> positions);

    private ShipmentTaskPosition changeQuantity(ShipmentTaskPosition newPosition) {
        return newPosition.toBulder().doneQuantity(newQuantity).build();
    }

    private ShipmentTaskPosition findPositionById() {
        List<ShipmentTaskPosition> items = getValue().positions();
        for (ShipmentTaskPosition item : items) {
            if (item.position().id().equals(id)) {
                return item;
            }
        }
        throw new NullPointerException("newPosition can't be null, check id");
    }

    private void checkInitArgs() {
        if (id == null || newQuantity < 0) {
            throw new IllegalArgumentException("you must set correct data");
        }
    }

}
