package com.example.dmitry.a1c_client.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.misc.CommonFilters;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Admin on 06.12.2016.
 */

public class ShipmentAdapterFragment extends Fragment{

    private static final String INDEX = "index";
    private int index;
    private ShipmentViewCallback provider;

    @BindView(R.id.etBarCode) EditText etBarCode;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvRequiredQuantity) TextView tvRequiredQuantity;
    @BindView(R.id.etQuantity) EditText etQuantity;

    @Override public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parent = getParentFragment();
        if(parent != null && parent instanceof ShipmentViewCallback){
            provider = (ShipmentViewCallback) parent;
        }else if (context instanceof ShipmentViewCallback){
            provider = (ShipmentViewCallback) context;
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
        View fragmentView = inflater.inflate(R.layout.shipment_form_item_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        return fragmentView;
    }

    public void setName(String name){
        tvName.setText(name);
    }

    public void setDescription(String description){
        tvDescription.setText(description);
    }

    public void setRequiredQuantity(int quantity){
        tvRequiredQuantity.setText(""+quantity);
    }

    public void setDoneQuantity(int quantity){
        etQuantity.setText(""+quantity);
    }

    public Observable<CharSequence> getBarCodeObservable(){
        return RxTextView.textChanges(etBarCode);
    }

    public Observable<CharSequence> getQuantityObservable(){
        return RxTextView.textChanges(etQuantity);
    }

    public static ShipmentAdapterFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        ShipmentAdapterFragment fragment = new ShipmentAdapterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface ShipmentViewCallback {
        ShipmentTaskPosition provide(int index);
        void onBarCodeInput(int index, String barcode);
        void onQuantityInput(int index, int quantity);
    }
//---------------------------Presenter----------------------
    private class Presenter{
        private ShipmentViewCallback callback;
        private int index;
        private ShipmentAdapterFragment view;
        private ShipmentTaskPosition position;
        private CompositeSubscription subscriptions;
        public Presenter(ShipmentAdapterFragment view, ShipmentViewCallback callback, int index) {
            this.view = view;
            this.callback = callback;
            this.index = index;
            position = callback.provide(index);
            subscriptions = new CompositeSubscription();
        }

        public void start(){
            subscribeOnBarCodeInput();
        }

        private void subscribeOnBarCodeInput() {
            subscriptions.add(view.getBarCodeObservable()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map(charSequence -> charSequence.toString())
                    .filter(CommonFilters::isValidBarCode)
                    .subscribe(barCode -> incrementQuantity(barCode)));
        }

    private void incrementQuantity(String barCode) {
        //TODO: continue writing
        callback.onBarCodeInput(index, barCode);
    }

    private void initViews(){
            view.setName(position.position().positionName());
            view.setDescription(position.position().description());
            view.setRequiredQuantity(position.requiredQuantity());
            view.setDoneQuantity(position.doneQuantity());
        }
    }
}
