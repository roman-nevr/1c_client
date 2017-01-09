package com.example.dmitry.a1c_client.android.views;


import android.view.View;

import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.adapters.IncomeDocumentsAdapter;
import com.example.dmitry.a1c_client.android.views.fragments.BaseListFragment;
import com.example.dmitry.a1c_client.android.views.income_task.IncomeTaskActivity;
import com.example.dmitry.a1c_client.di.DaggerIncomeFragmentComponent;
import com.example.dmitry.a1c_client.di.IncomeListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListView;

import java.util.List;

import javax.inject.Inject;

import static com.example.dmitry.a1c_client.android.MyApplication.log;

public class IncomeListFragment extends BaseListFragment implements IncomeListView {
    @Inject IncomeListPresenter presenter;

    private IncomeDocumentsAdapter adapter;

    protected void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerIncomeFragmentComponent.builder().mainComponent(mainComponent).incomeListModule(new IncomeListModule(this)).build().inject(this);
        log("init income");
        presenter.init();
    }

    @Override
    protected void refresh() {
        Dummy.addDummyIncomeDocument();
        presenter.update();
    }

    @Override
    public void setDocuments(List<IncomeDocument> documents) {
        log("set income");
        if (adapter == null) {
            adapter = new IncomeDocumentsAdapter(documents, this);
            recyclerView().setAdapter(adapter);
        }else {
            adapter.update(documents);
        }
    }



    @Override
    public void onStart() {
        log("start income");
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
        IncomeTaskActivity.start(getContext(), id);
    }
}
