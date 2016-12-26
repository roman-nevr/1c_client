package com.example.dmitry.a1c_client.presentation.entity;

import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentViewState {
    public List<ShipmentTaskPosition> initialPositions;
    public List<ShipmentTaskPosition> actualPositions;
    public boolean showOnlyActual;

    public ShipmentViewState(List<ShipmentTaskPosition> positions) {
        this.initialPositions = positions;
        this.actualPositions = calculateActualPositions(positions);
        this.showOnlyActual = true;
        markedItem = -1;
    }

    private int markedItem;
    public int size(){
        if(showOnlyActual){
            return actualPositions.size();
        }else {
            return initialPositions.size();
        }
    }
    public ShipmentTaskPosition get(int index){
        if(showOnlyActual){
            return actualPositions.get(index);
        }else {
            return initialPositions.get(index);
        }
    }
    public boolean hasMarkedItem(){
        return markedItem >= 0;
    }
    public void removeMarkedItem(){
        if (markedItem != -1 && showOnlyActual){
            actualPositions.remove(markedItem);
        }
    }

    private List<ShipmentTaskPosition> calculateActualPositions
                                    (List<ShipmentTaskPosition> positions){
        List<ShipmentTaskPosition> result = new ArrayList<>();
        for (ShipmentTaskPosition item : positions){
            if(item.doneQuantity() < item.requiredQuantity()){
                result.add(item);
            }
        }
        return result;
    }
}
