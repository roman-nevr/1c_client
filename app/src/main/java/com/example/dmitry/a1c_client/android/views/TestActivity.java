package com.example.dmitry.a1c_client.android.views;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.adapters.EquipDocumentsAdapter;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.misc.dummy.Dummy;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

import static android.view.View.GONE;

/**
 * Created by roma on 04.01.2017.
 */

public class TestActivity extends BaseActivity implements IOnItemClick {

    @BindView(R.id.progress) ProgressBar progressBar;
    @BindView(R.id.error_text) TextView errorText;
    @BindView(R.id.recycler_view) RecyclerView recyclerView;

    private EquipDocumentsAdapter adapter;

    @Override protected int provideLayoutId() {
        return R.layout.fragment_income;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override protected void onStart() {
        super.onStart();
        List<EquipDocument> documents = new ArrayList<>();
        documents = Dummy.EQUIP_DOCUMENTS;
        adapter = new EquipDocumentsAdapter(documents, this);
        recyclerView.setAdapter(adapter);
        progressBar.setVisibility(GONE);
    }

    @Override public void onItemClickAction(String id) {

    }

    public static void start(Context context){
        Intent intent = new Intent(context, TestActivity.class);
        context.startActivity(intent);
    }
}
