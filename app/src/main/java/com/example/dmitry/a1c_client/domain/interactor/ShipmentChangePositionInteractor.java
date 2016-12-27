package com.example.dmitry.a1c_client.domain.interactor;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.Enums;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskState;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.domain.entity.Enums.TransmissionState.received;

/**
 * Created by Admin on 27.12.2016.
 */

public class ShipmentChangePositionInteractor extends Interactor {

    @Inject StateKeeper<ShipmentTaskState> stateKeeper;

    private String id;
    private int newQuantity;

    @Inject
    public ShipmentChangePositionInteractor() {}

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
        List<ShipmentTaskPosition> positions = new ArrayList<>(stateKeeper.getValue().positions());
        int index = positions.indexOf(newPosition);
        newPosition = changeQuantity(newPosition);
        positions.set(index, newPosition);
        updateState(positions);
    }

    private void updateState(List<ShipmentTaskPosition> positions) {
        stateKeeper.change(state -> state.toBuilder()
                .positions(positions)
                .build());
    }

    private ShipmentTaskPosition changeQuantity(ShipmentTaskPosition newPosition) {
        return newPosition.toBulder().doneQuantity(newQuantity).build();
    }

    private ShipmentTaskPosition findPositionById() {
        List<ShipmentTaskPosition> items = stateKeeper.getValue().positions();
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
