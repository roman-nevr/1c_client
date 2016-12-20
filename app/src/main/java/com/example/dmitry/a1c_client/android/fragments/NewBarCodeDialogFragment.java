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

import com.example.dmitry.a1c_client.domain.IncomeTaskRepository;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogPresenter;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;

import javax.inject.Inject;

import ru.yoursolution.a1cclient.App;
import ru.yoursolution.a1cclient.R;
import ru.yoursolution.a1cclient.domain.model.DetailFormState;
import ru.yoursolution.a1cclient.implementations.DetailFormStateRepository;
import ru.yoursolution.a1cclient.presentation.ui.interfaces.INewBarCodeDialogFragmentConvention;
import ru.yoursolution.a1cclient.presentation.ui.presenters.NewBarCodeDialogFragmentPresenterImpl;
import rx.Observable;

/**
 * Created by Admin on 23.11.2016.
 */

public class NewBarCodeDialogFragment extends DialogFragment implements NewBarCodeDialogView {

    private View form;
    private Button btnYes;
    private Button btnNo;
    private EditText etVendorCode;
    private TextView tvBarCode;
    private TextView tvNomenklatura;

    @Inject NewBarCodeDialogPresenter presenter;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.new_barcode_dialog_layout, null, true);
        bindViews(form);

        repository = App.detailFormStateRepository;
        observable = repository.getObservable();
        presenter = new NewBarCodeDialogFragmentPresenterImpl(this);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        return builder.create();
    }

    private void bindViews(View form) {
        btnYes = (Button) form.findViewById(R.id.btnYes);
        btnNo = (Button) form.findViewById(R.id.btnNo);
        etVendorCode = (EditText) form.findViewById(R.id.etVendorCode);
        tvBarCode = (TextView) form.findViewById(R.id.tvBarCode);
        tvNomenklatura = (TextView) form.findViewById(R.id.tvNomenklatura);

        btnYes.setOnClickListener(v -> {
            presenter.onYesButtonClick();
        });
        btnNo.setOnClickListener(v -> {
            presenter.onNoButtonClick();
        });
    }


    @Override
    public void showNomenklatura(String arg) {
        tvNomenklatura.setText(arg);
    }

    @Override
    public void showBarCode(String arg) {
        tvBarCode.setText(arg);
    }

    @Override
    public EditText getVendorCodeView() {
        return etVendorCode;
    }

    @Override
    public void showYesButton() {
        btnYes.setClickable(true);
    }

    @Override
    public void hideYesButton() {
        btnYes.setClickable(false);
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.init();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        presenter.onStop();
    }
}
