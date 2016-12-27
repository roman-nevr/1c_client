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
import com.example.dmitry.a1c_client.android.interfaces.ShipmentTaskItemView.ShipmentViewCallback;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.misc.CommonFilters;
import com.jakewharton.rxbinding.widget.RxTextView;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;


/**
 * Created by Admin on 06.12.2016.
 */

public class ShipmentAdapterFragment extends Fragment {

    private static final String INDEX = "index";
    @BindView(R.id.etBarCode) EditText etBarCode;
    @BindView(R.id.tvName) TextView tvName;
    @BindView(R.id.tvDescription) TextView tvDescription;
    @BindView(R.id.tvRequiredQuantity) TextView tvRequiredQuantity;
    @BindView(R.id.etQuantity) EditText etQuantity;
    private int index;
    private ShipmentViewCallback callback;
    private Presenter presenter;

    public static ShipmentAdapterFragment newInstance(int index) {
        Bundle args = new Bundle();
        args.putInt(INDEX, index);
        ShipmentAdapterFragment fragment = new ShipmentAdapterFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Fragment parent = getParentFragment();
        if (parent != null && parent instanceof ShipmentViewCallback) {
            callback = (ShipmentViewCallback) parent;
        } else if (getActivity() instanceof ShipmentViewCallback) {
            callback = (ShipmentViewCallback) getActivity();
        }
        if (callback == null) {
            throw new UnsupportedOperationException("Context must implement ShipmentViewCallback");
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
        presenter = new Presenter(this, callback, index);
        return fragmentView;
    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.stop();
    }

    public void setName(String name) {
        tvName.setText(name);
    }

    public void setDescription(String description) {
        tvDescription.setText(description);
    }

    public void setRequiredQuantity(int quantity) {
        if (quantity > 0) {
            tvRequiredQuantity.setText("" + quantity);
        } else {
            tvRequiredQuantity.setText("");
        }
    }

    public void setDoneQuantity(int quantity) {
        etQuantity.setText("" + quantity);
    }

    public Observable<CharSequence> getBarCodeObservable() {
        return RxTextView.textChanges(etBarCode);
    }

    public Observable<CharSequence> getQuantityObservable() {
        return RxTextView.textChanges(etQuantity);
    }

    public void setQuantityError() {
        etQuantity.setError("");
    }

    private void setFocus() {
        etBarCode.requestFocus();
    }

    //---------------------------Presenter----------------------
    private class Presenter {
        private ShipmentViewCallback callback;
        private int index;
        private ShipmentAdapterFragment view;
        private ShipmentTaskPosition position;
        private CompositeSubscription subscriptions;

        public Presenter(ShipmentAdapterFragment view, ShipmentViewCallback callback, int index) {
            this.view = view;
            this.callback = callback;
            this.index = index;
            position = callback.getItem(index);
            subscriptions = new CompositeSubscription();
            initViews();
        }

        public void start() {
            subscribeOnBarCodeInput();
            subscribeOnQuantityInput();
            //subscribeOnQuantityError();
        }

        private void subscribeOnQuantityInput() {
            subscriptions.add(view.getQuantityObservable()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map(charSequence -> charSequence.toString())
                    .filter(CommonFilters::isValidNumber)
                    .observeOn(Schedulers.io())
                    .subscribe(quantity -> {
                        setQuantity(Integer.parseInt(quantity));
                    }));
        }

        private void subscribeOnQuantityError() {
            subscriptions.add(view.getQuantityObservable()
                    .debounce(500, TimeUnit.MILLISECONDS)
                    .map(charSequence -> charSequence.toString())
                    .filter(CommonFilters::isInvalidNumber)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(quantity -> {
                        if (Integer.parseInt(quantity) != 0) {
                            view.setQuantityError();
                        }
                    }));
        }

        private void subscribeOnBarCodeInput() {
            subscriptions.add(view.getBarCodeObservable()
                    .debounce(300, TimeUnit.MILLISECONDS)
                    .map(charSequence -> charSequence.toString())
                    .filter(CommonFilters::isValidBarCode)
                    .subscribe(barCode -> incrementQuantity(barCode)));
        }

        private void incrementQuantity(String barCode) {
            if (barCode.equals(position.position().barCode())) {
                callback.onQuantityChanges(index, position.doneQuantity() + 1);
            }
        }

        private void setQuantity(int quantity) {
            if (quantity != position.doneQuantity()) {
                callback.onQuantityChanges(index, quantity);
            }
        }

        private void initViews() {
            view.setFocus();
            view.setName(position.position().positionName());
            view.setDescription(position.position().description());
            view.setRequiredQuantity(position.requiredQuantity());

            view.setDoneQuantity(position.doneQuantity());
        }

        public void stop() {
            subscriptions.clear();
        }
    }
}
