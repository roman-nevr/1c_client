package com.example.dmitry.a1c_client.presentation.entity;

import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentViewState {
    public List<ShipmentTaskPosition> initialPositions;
    public List<ShipmentTaskPosition> actualPositions;
    public boolean showOnlyActual;

    public ShipmentViewState(List<ShipmentTaskPosition> positions, boolean showOnlyActual) {
        this.initialPositions = positions;
        this.actualPositions = calculateActualPositions(positions);
        this.showOnlyActual = showOnlyActual;
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
        markedItem = -1;
    }

    public boolean isLastVisiblePage(){
        return actualPositions.size() == 1;
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

    public void update(List<ShipmentTaskPosition> positions) {
        initialPositions = positions;
        Iterator<ShipmentTaskPosition> actualIterator=  actualPositions.iterator();
        ShipmentTaskPosition actualPosition = actualIterator.next();
        for(ShipmentTaskPosition position : positions){
            if(actualPosition.position() == position.position()){
                if(actualPosition.doneQuantity() != position.doneQuantity()){
                    updateActualPosition(actualPosition, position);
                }
                if(actualIterator.hasNext()){
                    actualPosition = actualIterator.next();
                }
            }
        }
    }

    private void updateActualPosition(ShipmentTaskPosition actualPosition, ShipmentTaskPosition position) {
        int index = actualPositions.indexOf(actualPosition);
        if(position.doneQuantity() == position.requiredQuantity()){
            markedItem = index;
        }else {
            actualPositions.set(index,position);
        }
    }
}
