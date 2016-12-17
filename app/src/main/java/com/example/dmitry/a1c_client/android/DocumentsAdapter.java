package com.example.dmitry.a1c_client.android;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.Document;

import java.text.DateFormat;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DocumentsAdapter  extends RecyclerView.Adapter<DocumentsAdapter.MyHolder>{
    private List<Document> documentList;
    private DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.SHORT);

    public DocumentsAdapter(List<Document> documentList) {
        this.documentList = documentList;
        setHasStableIds(true);
    }

    public void update(List<Document> documentList) {
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
        Document document = documentList.get(position);
        holder.docNumber.setText(document.docNumber());
        holder.docDate.setText(dateFormat.format(document.docDate()));
        holder.clientName.setText(document.client().name());
    }

    @Override
    public int getItemCount() {
        return documentList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.number) TextView docNumber;
        @BindView(R.id.date) TextView docDate;
        @BindView(R.id.client_name) TextView clientName;

        public MyHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
