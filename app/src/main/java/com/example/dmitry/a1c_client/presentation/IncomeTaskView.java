package com.example.dmitry.a1c_client.presentation;

import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;

/**
 * Created by roma on 18.12.2016.
 */

public interface IncomeTaskView {
    public void showProgress();
    public void hideProgress();
    public void showPosition(NomenclaturePosition position);
    public void showStorageInfo(String place, String element);
    public void hideStorageInfo();
    public void showMap(StoreMapObject storeMapObject);
    public void showBarCodeNotFoundDialog();
    public void showNewBarCodeDialog(String barCode);
    public void showNetErrorMessage();
    public void showNoRightsMessage();
    public void showEmptyState();
    public String getQuantity();
    public void showQuantityError();
}
