package com.example.dmitry.a1c_client.android.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dmitry.a1c_client.android.MyApplication.log;
import static java.text.DateFormat.MEDIUM;
import static java.text.DateFormat.SHORT;

public class EquipDocumentsAdapter extends RecyclerView.Adapter<EquipDocumentsAdapter.MyHolder>{
    private List<EquipDocument> documentList;
    private DateFormat dateFormat = utils.shortFormat;
    private IOnItemClick onItemClick;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public EquipDocumentsAdapter(List<EquipDocument> documentList, IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        this.documentList = documentList;
        setHasStableIds(true);
    }

    public void update(List<EquipDocument> documentList) {
        this.documentList = documentList;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //log("onCreateViewHolder start");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.equip_documents_item, parent, false);
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
        if (isPositionHeader(position)){
            bindHeader(holder);
        }else {
            bindItemHolder(holder, position);
        }
    }

    private void bindItemHolder(MyHolder holder, int position){
        //log("onBindViewHolder start");
        //log("onBindViewHolder 1");
        holder.number.setText(getItem(position).docNumber());
        //log("onBindViewHolder 3");
        holder.clientName.setText(getItem(position).client().name());
        //log("onBindViewHolder 4");
        holder.docDate.setText(dateFormat.format(getItem(position).docDate()));
        //log("onBindViewHolder");
    }

    private void bindHeader(MyHolder holder){
    }

    @Override public long getItemId(int position) {
        if(position == 0){
            return 0;
        }else {
            return getItem(position).hashCode();
        }

    }

    @Override
    public int getItemCount() {
        return documentList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    private EquipDocument getItem(int position) {
        return documentList.get(position - 1);
    }



   public static class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_number) TextView number;
        @BindView(R.id.tv_props_date) TextView docDate;
        @BindView(R.id.tv_client_name) TextView clientName;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
