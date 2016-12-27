package com.example.dmitry.a1c_client.presentation;

import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.widget.EditText;

import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;

import java.util.List;

import rx.Observable;

/**
 * Created by Admin on 23.12.2016.
 */

public interface ShipmentTaskView {

    void initProgressBar(int start, int max);
    ViewPager getViewPager();
    FragmentManager provideFragmentManager();
    void showProgress();
    void hideProgress();
    void onComplete();

    public interface Callback{
        void onShipmentComplete();
    }
}
