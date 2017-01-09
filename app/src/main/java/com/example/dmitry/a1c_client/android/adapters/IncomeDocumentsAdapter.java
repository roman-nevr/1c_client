package com.example.dmitry.a1c_client.android.adapters;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.IncomeDocument;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.interfaces.IOnItemClick;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dmitry.a1c_client.android.MyApplication.log;

public class IncomeDocumentsAdapter extends RecyclerView.Adapter<IncomeDocumentsAdapter.MyHolder>{
    private List<IncomeDocument> documentList;
    private DateFormat dateFormat = utils.shortFormat;
    private IOnItemClick onItemClick;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public IncomeDocumentsAdapter(List<IncomeDocument> documentList, IOnItemClick onItemClick) {
        this.onItemClick = onItemClick;
        this.documentList = documentList;
        setHasStableIds(true);
    }

    public void update(List<IncomeDocument> documentList) {
        this.documentList = documentList;
        notifyDataSetChanged();
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //log("onCreateViewHolder start");
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.income_documents_item, parent, false);
        MyHolder holder = new MyHolder(view);
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
        }else {
            bindItemHolder(holder, position);
        }
    }

    private void bindItemHolder(MyHolder holder, int position) {
        holder.tvNumber.setText(getItem(position).docNumber());
        holder.docDate.setText(dateFormat.format(getItem(position).docDate()));
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

    private IncomeDocument getItem(int position) {
        return documentList.get(position - 1);
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_number) TextView tvNumber;
        @BindView(R.id.tv_props_date) TextView docDate;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
