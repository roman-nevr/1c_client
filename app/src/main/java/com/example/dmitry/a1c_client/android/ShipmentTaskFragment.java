package com.example.dmitry.a1c_client.android;

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
import com.example.dmitry.a1c_client.android.fragments.ShipmentAdapterFragment.ShipmentViewCallback;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskPresenter;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by roma on 25.12.2016.
 */

public class ShipmentTaskFragment extends Fragment implements ShipmentTaskView, ShipmentViewCallback {

    @Inject ShipmentTaskPresenter presenter;
    @BindView(R.id.pbCollecting) ProgressBar taskProgressBar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.progressBar) ProgressBar progress;

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
    public FragmentManager provideFragmentManager() {
        return getChildFragmentManager();
    }

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.shipment_act_fragment_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        initDI();
        presenter.setView(this);
        presenter.init();
        return fragmentView;
    }

    private void initDI() {
        ShipmentTaskComponent taskComponent = ((MyApplication)getActivity().getApplication())
                .getShipmentTaskComponent();
        DaggerShipmentTaskViewComponent.builder().shipmentTaskComponent(taskComponent)
                .build().inject(this);

    }

    @Override public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter.start();
    }

    @Override public ShipmentTaskPosition provide(int index) {
        return presenter.getPosition(index);
    }
}
