package com.example.dmitry.a1c_client.android.income_task;

import android.os.Bundle;

import com.example.dmitry.a1c_client.android.BaseActivity;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;

import rx.Observable;

/**
 * Created by Admin on 22.12.2016.
 */

public class NewBarCodeDialogActivity extends BaseActivity implements NewBarCodeDialogView {

    @Override protected int provideLayoutId() {
        return 0;
    }

    @Override protected void initDi(Bundle savedInstanceState) {

    }

    @Override public void showNomenklatura(NomenclaturePosition position) {

    }

    @Override public Observable<CharSequence> getVendorCodeObservable() {
        return null;
    }

    @Override public void showYesButton() {

    }

    @Override public void hideYesButton() {

    }

    @Override public void dismiss() {

    }

    @Override public void showError() {

    }
}
