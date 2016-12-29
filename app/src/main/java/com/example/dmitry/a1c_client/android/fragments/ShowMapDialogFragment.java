package com.example.dmitry.a1c_client.android.fragments;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.custom_view.StoreMapView;
import com.example.dmitry.a1c_client.di.income_task.DaggerNewBarCodeViewComponent;
import com.example.dmitry.a1c_client.di.income_task.DaggerShowMapComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogPresenter;
import com.example.dmitry.a1c_client.presentation.NewBarCodeDialogView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Admin on 23.11.2016.
 */

public class ShowMapDialogFragment extends DialogFragment{

    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    private View form;
    @BindView(R.id.map) StoreMapView mapView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDI();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        form = getActivity().getLayoutInflater().inflate(R.layout.store_map_layout, null, true);
        mapView = (StoreMapView) form.findViewById(R.id.map);
        mapView.setStoreMapObject(stateKeeper.getValue().storeMapObject());
        ButterKnife.bind(this, form);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    private void initDI() {
        IncomeTaskComponent taskComponent = ((MyApplication)getActivity().getApplicationContext())
                .getIncomeTaskComponent();
        DaggerShowMapComponent.builder().incomeTaskComponent(taskComponent)
                .build().inject(this);
    }

    public static DialogFragment newInstance(){
        DialogFragment fragment = new ShowMapDialogFragment();
        //fragment.setCancelable(false);
        return fragment;
    }
}
