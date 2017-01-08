package com.example.dmitry.a1c_client.android.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dmitry.a1c_client.android.MyApplication.log;

public class EquipDocumentsAdapter extends RecyclerView.Adapter<EquipDocumentsAdapter.MyHolder>{
    private List<EquipDocument> documentList = new ArrayList<>();
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
    private IOnItemClick onItemClick;

    public EquipDocumentsAdapter(List<EquipDocument> documentList, IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        this.documentList.clear();
        this.documentList.addAll(documentList);

        //this.documentList = documentList;
        setHasStableIds(true);
    }

    public void update(List<EquipDocument> documentList) {
        this.documentList = documentList;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //log("onCreateViewHolder start");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_documents2, parent, false);
        //log("onCreateViewHolder 1");
        MyHolder holder = new MyHolder(view);
        //log("onCreateViewHolder 2");
        view.setOnClickListener(it -> {
            int adapterPosition = holder.getAdapterPosition();
            if(adapterPosition != RecyclerView.NO_POSITION){
                onItemClick.onItemClickAction(documentList.get(adapterPosition).id());
            }
        });
        //log("onCreateViewHolder");
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        //log("onBindViewHolder start");
        EquipDocument document = documentList.get(position);
        //log("onBindViewHolder 1");
        holder.id.setText(document.id());
        //log("onBindViewHolder 2");
        holder.docNumber.setText(document.docNumber());
        //log("onBindViewHolder 3");
        holder.clientName.setText(document.client().name());
        //log("onBindViewHolder 4");
        holder.docDate.setText(dateFormat.format(document.docDate()));
        //log("onBindViewHolder");
    }

    @Override public long getItemId(int position) {
        return documentList.get(position).hashCode();
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

   public static class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvId) TextView id;
        @BindView(R.id.tvNumber) TextView docNumber;
        @BindView(R.id.tvPropsDate) TextView docDate;
        @BindView(R.id.tvClientName) TextView clientName;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
