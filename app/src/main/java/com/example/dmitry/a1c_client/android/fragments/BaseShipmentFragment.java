package com.example.dmitry.a1c_client.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.interfaces.ShipmentTaskItemView.ShipmentViewCallback;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Admin on 27.12.2016.
 */

public abstract class BaseShipmentFragment extends Fragment implements ShipmentTaskView, ShipmentViewCallback {

    @BindView(R.id.pbCollecting) ProgressBar taskProgressBar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.progressBar) ProgressBar progress;

    private Callback callback;

    @Override public void showProgress() {
        progress.setVisibility(VISIBLE);
        taskProgressBar.setVisibility(GONE);
        viewPager.setVisibility(GONE);
    }

    @Override public void hideProgress() {
        progress.setVisibility(GONE);
        taskProgressBar.setVisibility(VISIBLE);
        viewPager.setVisibility(VISIBLE);
    }

    @Override
    public void initProgressBar(int start, int max) {
        taskProgressBar.setMax(max);
        taskProgressBar.incrementProgressBy(start);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof Callback){
            callback = (Callback) getActivity();
        }else {
            throw new UnsupportedOperationException("parent must implement ShipmentTaskView.Callback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.shipment_act_fragment_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        initDI();
        initPresenter();
        return fragmentView;
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return getChildFragmentManager();
    }

    protected abstract void initDI();

    protected abstract void initPresenter();

    @Override
    public abstract ShipmentTaskPosition getItem(int index);

    @Override
    public abstract void onQuantityChanges(int index, int quantity);

    @Override
    public void onComplete() {
        callback.onShipmentComplete();
    }
}
