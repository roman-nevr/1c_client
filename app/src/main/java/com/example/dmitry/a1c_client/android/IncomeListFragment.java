package com.example.dmitry.a1c_client.android;


import android.view.View;

import com.example.dmitry.a1c_client.android.adapters.IncomeDocumentsAdapter;
import com.example.dmitry.a1c_client.android.adapters.ShipmentDocumentsAdapter;
import com.example.dmitry.a1c_client.android.fragments.BaseListFragment;
import com.example.dmitry.a1c_client.android.income_task.IncomeTaskActivity;
import com.example.dmitry.a1c_client.android.shipment.ShipmentTaskActivity;
import com.example.dmitry.a1c_client.di.DaggerIncomeFragmentComponent;
import com.example.dmitry.a1c_client.di.DaggerShipmentFragmentComponent;
import com.example.dmitry.a1c_client.di.IncomeListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.di.ShipmentListModule;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListView;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.ShipmentListView;

import java.util.List;

import javax.inject.Inject;

public class IncomeListFragment extends BaseListFragment implements IncomeListView {
    @Inject IncomeListPresenter presenter;

    private IncomeDocumentsAdapter adapter;

    protected void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerIncomeFragmentComponent.builder().mainComponent(mainComponent).incomeListModule(new IncomeListModule(this)).build().inject(this);

        presenter.init();
    }

    @Override
    public void setDocuments(List<IncomeDocument> documents) {
        if (adapter == null) {
            adapter = new IncomeDocumentsAdapter(documents, this);
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
    public void onItemClickAction(View view, String id) {
        IncomeTaskActivity.start(getContext());
    }
}
