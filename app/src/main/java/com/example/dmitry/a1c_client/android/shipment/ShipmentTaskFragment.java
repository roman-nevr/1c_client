package com.example.dmitry.a1c_client.android.shipment;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.fragments.BaseShipmentFragment;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskPresenter;

import javax.inject.Inject;

/**
 * Created by roma on 25.12.2016.
 */

public class ShipmentTaskFragment extends BaseShipmentFragment {

    @Inject ShipmentTaskPresenter presenter;

    @Override
    protected void initPresenter() {
        presenter.setView(this);
        presenter.init();
    }

    @Override
    public ShipmentTaskPosition getItem(int index) {
        return presenter.getPosition(index);
    }

    @Override
    public void onQuantityChanges(int index, int quantity) {
        presenter.onQuantityChanges(index, quantity);
    }

    @Override
    protected void initDI() {
        ShipmentTaskComponent taskComponent = ((MyApplication) getActivity().getApplication())
                .getShipmentTaskComponent();
        DaggerShipmentTaskViewComponent.builder().shipmentTaskComponent(taskComponent)
                .build().inject(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }
}
