package com.example.dmitry.a1c_client.android.views;


import android.view.View;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.adapters.ShipmentDocumentsAdapter;
import com.example.dmitry.a1c_client.android.views.fragments.BaseListFragment;
import com.example.dmitry.a1c_client.android.views.shipment.ShipmentTaskActivity;
import com.example.dmitry.a1c_client.di.DaggerShipmentFragmentComponent;
import com.example.dmitry.a1c_client.di.ShipmentListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListView;

import java.util.List;

import javax.inject.Inject;

public class ShipmentListFragment extends BaseListFragment implements ShipmentListView {
    @Inject ShipmentListPresenter presenter;

    private ShipmentDocumentsAdapter adapter;

    protected void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerShipmentFragmentComponent.builder().mainComponent(mainComponent).shipmentListModule(new ShipmentListModule(this)).build().inject(this);
        presenter.init();
    }

    @Override
    public void setDocuments(List<ShipmentDocument> documents) {
        if (adapter == null) {
            adapter = new ShipmentDocumentsAdapter(documents, this);
            recyclerView().setAdapter(adapter);
        }else {
            adapter.update(documents);
        }
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

    @Override
    public void onItemClickAction(String id) {
        ShipmentTaskActivity.start(getContext());
    }
}
