package com.example.dmitry.a1c_client.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import java.util.List;


/**
 * Created by Admin on 18.11.2016.
 */

public class EquipActTableAdapter
        extends RecyclerView.Adapter<EquipActTableAdapter.EquipActTableViewHolder> {

    private List<ShipmentTaskPosition> items;

    public EquipActTableAdapter(List<ShipmentTaskPosition> items) {
        this.items = items;
    }

    @Override
    public EquipActTableViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        System.out.println(parent.getContext());
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.equip_act_contans_item, parent, false);
        return new EquipActTableViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final EquipActTableViewHolder holder, final int index) {
        System.out.println("bind view " + items.get(index).position().positionName());
        holder.tableRowItem = items.get(index);
        holder.tvPositionName.setText(items.get(index).position().positionName());
        holder.tvDoneQuantity.setText("" + items.get(index).doneQuantity());
        holder.tvRequiredQuantity.setText("" + items.get(index).requiredQuantity());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class EquipActTableViewHolder extends RecyclerView.ViewHolder {
        public final View holderView;
        public final TextView tvPositionName, tvDoneQuantity, tvRequiredQuantity;
        public ShipmentTaskPosition tableRowItem;

        public EquipActTableViewHolder(View view) {
            super(view);
            holderView = view;
            tvPositionName = (TextView) view.findViewById(R.id.tvPositionName);
            tvDoneQuantity = (TextView) view.findViewById(R.id.tvDoneQuantity);
            tvRequiredQuantity = (TextView) view.findViewById(R.id.tvRequaredQuantity);
        }
    }

    public void update(List<ShipmentTaskPosition> items){
        this.items = items;
        notifyDataSetChanged();
    }
}