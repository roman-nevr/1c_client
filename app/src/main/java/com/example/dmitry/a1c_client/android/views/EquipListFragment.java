package com.example.dmitry.a1c_client.android.views;


import android.view.View;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.adapters.EquipDocumentsAdapter;
import com.example.dmitry.a1c_client.android.views.equipment.EquipmentTaskActivity;
import com.example.dmitry.a1c_client.android.views.fragments.BaseListFragment;
import com.example.dmitry.a1c_client.di.DaggerEquipFragmentComponent;
import com.example.dmitry.a1c_client.di.EquipListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.presentation.document_list.EquipListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.EquipListView;

import java.util.List;

import javax.inject.Inject;

public class EquipListFragment extends BaseListFragment implements EquipListView {
    @Inject EquipListPresenter presenter;

    private EquipDocumentsAdapter adapter;

    protected void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerEquipFragmentComponent.builder().mainComponent(mainComponent).equipListModule(new EquipListModule(this)).build().inject(this);

        presenter.init();
    }

    @Override
    public void setDocuments(List<EquipDocument> documents) {
        if (adapter == null) {
            adapter = new EquipDocumentsAdapter(documents, this);
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
        EquipmentTaskActivity.start(getContext());
    }
}
