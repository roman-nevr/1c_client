package com.example.dmitry.a1c_client.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskActivity extends BaseActivity {

    public static final String SHIPMENT_FRAGMENT = "shipment";

    @Override protected int provideLayoutId() {
        return R.layout.activity_main;
    }

    @Override protected void initDi(Bundle savedInstanceState) {
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ShipmentTaskFragment shipmentTaskFragment = new ShipmentTaskFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, shipmentTaskFragment, SHIPMENT_FRAGMENT).commit();
        }
    }

    public static void start(Context context){
        Intent intent = new Intent(context, ShipmentTaskActivity.class);
        context.startActivity(intent);
    }
}
