package com.example.dmitry.a1c_client.android.views;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.adapters.EquipDocumentsAdapter;
import com.example.dmitry.a1c_client.android.views.equipment.EquipmentTaskActivity;
import com.example.dmitry.a1c_client.android.views.fragments.BaseListFragment;
import com.example.dmitry.a1c_client.di.DaggerEquipFragmentComponent;
import com.example.dmitry.a1c_client.di.EquipListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;
import com.example.dmitry.a1c_client.presentation.document_list.EquipListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.EquipListView;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dmitry.a1c_client.android.MyApplication.log;

public class EquipListFragment extends BaseListFragment implements EquipListView {
    @Inject EquipListPresenter presenter;

    private EquipDocumentsAdapter adapter;

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.error_text) TextView errorText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    protected void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerEquipFragmentComponent.builder().mainComponent(mainComponent).equipListModule(new EquipListModule(this)).build().inject(this);

        //log("init equip");
        presenter.init();
    }

    @Override
    protected void refresh() {
        Dummy.addDummyEquipDocument();
        presenter.update();
    }

    @Override
    public void setDocuments(List<EquipDocument> documents) {
        //log("set equip");
        if (adapter == null) {
            adapter = new EquipDocumentsAdapter(documents, this);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.update(documents);
        }
    }

    @Override
    public void onStart() {
        //log("start equip");
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        //log("stop equip");
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onItemClickAction(String id) {
        EquipmentTaskActivity.start(getContext(), id);
    }
}
