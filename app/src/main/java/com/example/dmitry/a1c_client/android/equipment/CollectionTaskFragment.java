package com.example.dmitry.a1c_client.android.equipment;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.fragments.BaseShipmentFragment;
import com.example.dmitry.a1c_client.di.equipment.DaggerCollectionTaskViewComponent;
import com.example.dmitry.a1c_client.di.equipment.EquipmentTaskComponent;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.presentation.CollectionPresenter;

import javax.inject.Inject;

/**
 * Created by Admin on 27.12.2016.
 */

public class CollectionTaskFragment extends BaseShipmentFragment {
    @Inject CollectionPresenter presenter;

    @Override
    protected void initDI() {
        EquipmentTaskComponent component = ((MyApplication) getActivity()
                .getApplication()).getEquipmentTaskComponent();
        DaggerCollectionTaskViewComponent.builder().equipmentTaskComponent(component)
                .build().inject(this);
    }

    @Override
    protected void initPresenter() {
        //presenter.set
    }

    @Override
    public void onOkButtonClick(int queryId) {

    }

    @Override
    public void onCancelButtonClick(int queryId) {

    }

    @Override
    public ShipmentTaskPosition getItem(int index) {
        return null;
    }

    @Override
    public void onQuantityChanges(String id, int quantity) {

    }
}