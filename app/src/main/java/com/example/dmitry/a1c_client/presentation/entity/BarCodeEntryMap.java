package com.example.dmitry.a1c_client.presentation.entity;

import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Admin on 26.12.2016.
 */

public class BarCodeEntryMap {

    public static BarCodeEntryMap EMPTY = new BarCodeEntryMap(Collections.EMPTY_LIST);

    private List<Value> values;
    private Value searchObject = new Value("", null);

    public BarCodeEntryMap(List<Kit> kits) {
        values = new ArrayList<>();
        for(Kit kit : kits){
            for (ShipmentTaskPosition item : kit.kitContent()){
                add(item.position().barCode(), kit);
            }
        }
    }

    public Kit get(String barCode){
        searchObject.barCode = barCode;
        int index = values.indexOf(searchObject);
        if(index < 0){
            return Kit.EMPTY;
        }else {
            List<Kit> entries = values.get(index).entries;
            for(Kit kit : entries){
                for(ShipmentTaskPosition item : kit.kitContent()){
                    if (item.position().barCode().equals(barCode)
                            && (item.requiredQuantity() > item.doneQuantity())){
                        return kit;
                    }
                }
            }
        }
        return Kit.EMPTY;
    }

    private void add(String barCode, Kit kit){
        boolean hasBarCode = false;
        int index = -1;
        for (Value value : values){
            if (barCode.equals(value.barCode)){
                hasBarCode = true;
                index = values.indexOf(value);
            }
        }
        if(hasBarCode){
            values.get(index).add(kit);
        }else {
            values.add(new Value(barCode, kit));
        }
    }

    private class Value{
        String barCode;
        List<Kit> entries;

        public Value(String barCode, Kit entry) {
            this.barCode = barCode;
            this.entries = new ArrayList<>();
            this.entries.add(entry);
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Value value = (Value) o;

            return barCode.equals(value.barCode);

        }

        @Override public int hashCode() {
            return barCode.hashCode();
        }

        void add(Kit kit){
            entries.add(kit);
        }
    }
}
