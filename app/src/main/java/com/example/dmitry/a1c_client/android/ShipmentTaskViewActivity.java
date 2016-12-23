package com.example.dmitry.a1c_client.android;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskViewActivity extends BaseActivity implements ShipmentTaskView {
    @Override protected int provideLayoutId() {
        return 0;
    }

    @Override protected void initDi(Bundle savedInstanceState) {
    }

    @Override public void initProgressBar(int start, int max) {

    }

    @Override public ViewPager getViewPager() {
        return null;
    }
}
