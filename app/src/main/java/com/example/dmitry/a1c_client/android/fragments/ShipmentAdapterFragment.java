package com.example.dmitry.a1c_client.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;


/**
 * Created by Admin on 06.12.2016.
 */

public class ShipmentAdapterFragment extends Fragment{

    private static final String INDEX = "index";
    private int index;
    private PositionProvider provider;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parent = getParentFragment();
        if(parent != null && parent instanceof PositionProvider){
            provider = (PositionProvider) parent;
        }
        if (context instanceof PositionProvider){
            provider = (PositionProvider) context;
        }
        if(provider == null){
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        index = bundle.getInt(INDEX);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.shipment_form_item_layout, container, false);
    }

    public static ShipmentAdapterFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        ShipmentAdapterFragment fragment = new ShipmentAdapterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface PositionProvider{
        ShipmentTaskPosition provide(int index);
    }
}
