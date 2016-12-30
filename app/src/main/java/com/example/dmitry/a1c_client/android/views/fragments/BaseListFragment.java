package com.example.dmitry.a1c_client.android.views.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.presentation.interfaces.DocumentsListView;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Admin on 29.12.2016.
 */

public abstract class BaseListFragment extends Fragment implements DocumentsListView, IOnItemClick {

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.error_text) TextView errorText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_income, container, false);
        initDi();
        ButterKnife.bind(this, view);
        //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    protected abstract void initDi();

    protected RecyclerView recyclerView(){
        return recyclerView;
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
}
