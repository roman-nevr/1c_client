package com.example.dmitry.a1c_client.presentation;

import android.widget.Button;
import android.widget.EditText;

import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

/**
 * Created by roma on 18.12.2016.
 */

public interface IncomeTaskView {
    public void showProgress();
    public void hideProgress();
    public void showPosition(NomenclaturePosition position);
    public void showStorageInfo();
    /*public Button btnMap();
    public EditText etBarCode();
    public EditText etQuantity();*/
}
