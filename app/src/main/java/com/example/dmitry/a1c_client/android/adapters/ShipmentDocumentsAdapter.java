package com.example.dmitry.a1c_client.android.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShipmentDocumentsAdapter extends RecyclerView.Adapter<ShipmentDocumentsAdapter.MyHolder>{
    private List<ShipmentDocument> documentList;
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);
    private IOnItemClick onItemClick;

    public ShipmentDocumentsAdapter(List<ShipmentDocument> documentList, IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        this.documentList = documentList;
        //setHasStableIds(true);
    }

    public void update(List<ShipmentDocument> documentList) {
        this.documentList = documentList;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_documents, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        ShipmentDocument document = documentList.get(position);
        holder.document = document;
        holder.id.setText(document.id());
        holder.docNumber.setText(document.docNumber());
        holder.docDate.setText(dateFormat.format(document.docDate()));
        holder.clientName.setText(document.client().name());
        holder.holderView.setOnClickListener(v -> onItemClick.onItemClickAction(v, holder.document.id()));
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tvId) TextView id;
        @BindView(R.id.tvNumber) TextView docNumber;
        @BindView(R.id.tvPropsDate) TextView docDate;
        @BindView(R.id.tvClientName) TextView clientName;
        public ShipmentDocument document;
        public final View holderView;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            holderView = itemView;
        }
    }
}
