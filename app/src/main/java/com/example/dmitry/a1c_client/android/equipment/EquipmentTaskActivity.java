package com.example.dmitry.a1c_client.android.equipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.BaseActivity;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskView.Callback;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentTaskActivity extends BaseActivity
        implements Callback, ShipmentTaskView.Callback {

    public static final String TAG = "123";

    @Override
    protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            EquipmentTaskFragment equipFragment = new EquipmentTaskFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container,
                    equipFragment, TAG).commit();
        }
    }

    @Override
    public void onEquipmentComplete() {
    }

    @Override
    public void onShipmentComplete() {

    }

    public static void start(Context context){
        Intent intent = new Intent(context, EquipmentTaskActivity.class);
        context.startActivity(intent);
    }
}
