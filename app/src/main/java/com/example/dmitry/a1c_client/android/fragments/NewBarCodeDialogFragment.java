package com.example.dmitry.a1c_client.android.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.di.income_task.DaggerNewBarCodeViewComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.NewBarCodeViewModule;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogPresenter;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;

/**
 * Created by Admin on 23.11.2016.
 */

public class NewBarCodeDialogFragment extends DialogFragment implements NewBarCodeDialogView {

    @Inject NewBarCodeDialogPresenter presenter;
    private View form;
    @BindView(R.id.etVendorCode) EditText etVendorCode;
    @BindView(R.id.tvBarCode) TextView tvBarCode;
    @BindView(R.id.tvNomenklatura) TextView tvNomenklatura;
    @BindView (R.id.btnYes) Button btnYes;
    @BindView (R.id.btnNo) Button btnNo;

    public static final String BARCODE = "barCode";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.new_barcode_dialog_layout, null, true);
        ButterKnife.bind(this, form);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        hideYesButton();
        initDI();
        return builder.create();
    }

    private void initDI() {
        IncomeTaskComponent taskComponent = ((MyApplication)getActivity().getApplicationContext())
                .getIncomeTaskComponent();
        DaggerNewBarCodeViewComponent.builder().incomeTaskComponent(taskComponent)
                .newBarCodeViewModule(new NewBarCodeViewModule(this)).build().inject(this);
    }

    @Override public void onStart() {
        super.onStart();
        presenter.setView(this);
        presenter.start();
    }

    @Override public void onStop() {
        super.onStop();
        presenter.stop();
    }

    @OnClick(R.id.btnYes)
    public void onYesClick(){
        presenter.onYesButtonClick();
    };

    @OnClick(R.id.btnNo)
    public void onNoClick(){
        presenter.onCancelButtonClick();
    };

    @Override public void showNomenklatura(NomenclaturePosition position) {
        tvNomenklatura.setText(position.positionName());
    }

    @Override public Observable<CharSequence> getVendorCodeObservable() {
        return RxTextView.textChanges(etVendorCode);
    }

    @Override public void showYesButton() {
        btnYes.setEnabled(true);
    }

    @Override public void hideYesButton() {
        btnYes.setEnabled(false);
    }

    @Override public void showError() {
        utils.snackBarLong(tvNomenklatura, "Произошла ошибка");
    }

    public static DialogFragment newInstance(String barCode){
        DialogFragment fragment = new NewBarCodeDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(BARCODE, barCode);
        fragment.setArguments(bundle);
        return fragment;
    }
}
