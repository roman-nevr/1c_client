package com.example.dmitry.a1c_client.android.views.shipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.BaseActivity;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;

import static com.example.dmitry.a1c_client.presentation.ShipmentTaskView.ShipmentCallback;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskActivity extends BaseActivity implements ShipmentCallback, MessageCallBack {

    public static final String SHIPMENT_FRAGMENT = "shipment";
    public static final int FINAL = 4;

    public static void start(Context context) {
        Intent intent = new Intent(context, ShipmentTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initDi(Bundle savedInstanceState) {
        super.initDi(savedInstanceState);
        ShipmentTaskComponent component = ((MyApplication)getApplication()).getShipmentTaskComponent();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.frame_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ShipmentTaskFragment shipmentTaskFragment = new ShipmentTaskFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, shipmentTaskFragment, SHIPMENT_FRAGMENT).commit();
        }
    }

    @Override
    public void onShipmentComplete() {
        showMessage("Задание выполнено \nНажмите Ок для завершения", FINAL);
    }

    private void showMessage(String message, int id) {
        DialogFragment fragment = MessageDialogFragment.newInstance(message, id);
        fragment.show(getSupportFragmentManager(), "show");
    }


    @Override
    public void onMessageButtonClick(int id) {
        if (id == FINAL) {
            clearComponent();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearComponent();
    }

    private void clearComponent() {
        ((MyApplication) getApplication()).clearShipmentTaskComponent();
    }
}
