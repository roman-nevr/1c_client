package com.example.dmitry.a1c_client.android.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogPresenter;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;

import org.w3c.dom.Text;

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

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.new_barcode_dialog_layout, null, true);
        ButterKnife.bind(this, form);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        return builder.create();
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

    @Override public void showBarCode(String arg) {
    }

    @Override public Observable<CharSequence> getVendorCodeObservable() {
        return null;
    }

    @Override public void showYesButton() {

    }

    @Override public void hideYesButton() {

    }

    @Override public void showError() {

    }
}
