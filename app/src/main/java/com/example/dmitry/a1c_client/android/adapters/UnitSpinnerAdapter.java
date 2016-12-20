package com.example.dmitry.a1c_client.android.adapters;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dmitry.a1c_client.domain.entity.Unit;

import java.util.List;

/**
 * Created by Admin on 19.12.2016.
 */

public class UnitSpinnerAdapter extends ArrayAdapter{
    private Context context;
    private int textViewResourceId;
    private List<Unit> units;
    public UnitSpinnerAdapter(Context context,@LayoutRes int textViewResourceId,
                         List<Unit> units) {
        super(context, textViewResourceId, units);
        setDropDownViewResource(textViewResourceId);
        this.context = context;
        this.textViewResourceId = textViewResourceId;
        this.units = units;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, textViewResourceId, null);
        }
        ((TextView)convertView).setText(units.get(position).name());
        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(context, textViewResourceId, null);
        }
        ((TextView)convertView).setText(units.get(position).name());
        convertView.setPadding(10, 10, 10, 10);
        return convertView;
    }

    public void clearItems(){
        units = null;
        notifyDataSetChanged();
    }
}