package com.example.dmitry.a1c_client.android.views.shipment;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.fragments.BaseShipmentFragment;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;
import com.example.dmitry.a1c_client.presentation.BaseShipmentPresenter;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskPresenter;

import javax.inject.Inject;

/**
 * Created by roma on 25.12.2016.
 */

public class ShipmentTaskFragment extends BaseShipmentFragment {

    @Inject ShipmentTaskPresenter presenter;

    @Override
    protected BaseShipmentPresenter presenter() {
        return presenter;
    }



    @Override
    protected void initDI() {
        ShipmentTaskComponent taskComponent = ((MyApplication) getActivity().getApplication())
                .getShipmentTaskComponent();
        DaggerShipmentTaskViewComponent.builder().shipmentTaskComponent(taskComponent)
                .build().inject(this);

    }

}
