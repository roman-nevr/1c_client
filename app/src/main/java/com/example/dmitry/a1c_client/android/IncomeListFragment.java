package com.example.dmitry.a1c_client.android;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.adapters.DocumentsAdapter;
import com.example.dmitry.a1c_client.android.income_task.IncomeTaskActivity;
import com.example.dmitry.a1c_client.di.DaggerIncomeFragmentComponent;
import com.example.dmitry.a1c_client.di.IncomeListModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.Document;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListPresenter;
import com.example.dmitry.a1c_client.presentation.document_list.IncomeListView;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class IncomeListFragment extends Fragment implements IncomeListView, IOnItemClick {
    @Inject
    IncomeListPresenter presenter;
    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.error_text) TextView errorText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;
    private DocumentsAdapter adapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        initDi();
        ButterKnife.bind(this, view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    private void initDi() {
        MainComponent mainComponent = ((MyApplication) getActivity().getApplication()).getMainComponent();
        DaggerIncomeFragmentComponent.builder().mainComponent(mainComponent).incomeListModule(new IncomeListModule(this)).build().inject(this);
        presenter.init();
    }

    @Override
    public void setDocuments(List<Document> documents) {
        if (adapter == null) {
            adapter = new DocumentsAdapter(documents, this);
            recyclerView.setAdapter(adapter);
        }else {
            adapter.update(documents);
        }
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
        errorText.setVisibility(GONE);
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void showError() {
        progressBar.setVisibility(GONE);
        errorText.setVisibility(VISIBLE);
        recyclerView.setVisibility(GONE);
    }

    @Override
    public void hideError() {
        errorText.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showDocuments() {
        progressBar.setVisibility(GONE);
        errorText.setVisibility(GONE);
        recyclerView.setVisibility(VISIBLE);
    }

    @Override
    public void hideDocuments() {
        recyclerView.setVisibility(View.INVISIBLE);
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
        ShipmentTaskActivity.start(getContext());
    }
}
