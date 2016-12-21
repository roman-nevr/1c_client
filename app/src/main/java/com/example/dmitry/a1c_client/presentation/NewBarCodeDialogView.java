package com.example.dmitry.a1c_client.presentation;

import android.widget.EditText;

import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;

import rx.Observable;

/**
 * Created by Admin on 20.12.2016.
 */

public interface NewBarCodeDialogView {
    void showNomenklatura(NomenclaturePosition position);
    Observable<CharSequence> getVendorCodeObservable();
    void showYesButton();
    void hideYesButton();
    void dismiss();
    void showError();
}
