package com.example.dmitry.a1c_client.android.interfaces;

import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import rx.Observable;

/**
 * Created by Admin on 26.12.2016.
 */

public interface ShipmentTaskItemView {
    interface ItemView{
        void setBarCode(String barCode);
        void setBarCodeHint(String barCode);
        void setName(String name);
        void setDescription(String description);
        void setRequiredQuantity(int quantity);
        void setDoneQuantity(int quantity);
        Observable<CharSequence> getBarCodeObservable();
        Observable<CharSequence> getQuantityObservable();
    }
    interface ShipmentViewCallback {
        ShipmentTaskPosition getItem(int index);
        void onQuantityChanges(String id, int quantity);
    }
}
