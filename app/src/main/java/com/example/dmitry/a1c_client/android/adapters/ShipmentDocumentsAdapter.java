package com.example.dmitry.a1c_client.android.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.EquipDocument;
import com.example.dmitry.a1c_client.domain.entity.ShipmentDocument;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.text.DateFormat.SHORT;

public class ShipmentDocumentsAdapter extends RecyclerView.Adapter<ShipmentDocumentsAdapter.MyHolder>{
    private List<ShipmentDocument> documentList;
    private DateFormat dateFormat = utils.shortFormat;
    private IOnItemClick onItemClick;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shipment_documents_item, parent, false);
        MyHolder holder = new MyHolder(view);
        view.setOnClickListener(it -> {
            int adapterPosition = holder.getAdapterPosition();
            if(adapterPosition != RecyclerView.NO_POSITION){
                onItemClick.onItemClickAction(documentList.get(adapterPosition).id());
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {
        if (isPositionHeader(position)){

        }else {
            bindItemHolder(holder, position);
        }
    }

    private void bindItemHolder(MyHolder holder, int position) {
        holder.id.setText(getItem(position).id());
        holder.docNumber.setText(getItem(position).docNumber());
        holder.docDate.setText(dateFormat.format(getItem(position).docDate()));
        holder.clientName.setText(getItem(position).client().name());
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

    private ShipmentDocument getItem(int position) {
        return documentList.get(position - 1);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_doc_number) TextView id;
        @BindView(R.id.tv_order_number) TextView docNumber;
        @BindView(R.id.tvPropsDate) TextView docDate;
        @BindView(R.id.tv_client_name) TextView clientName;
        public ShipmentDocument document;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
