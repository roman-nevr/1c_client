package com.example.dmitry.a1c_client.presentation;

import android.support.v7.widget.RecyclerView;

import com.example.dmitry.a1c_client.domain.entity.EquipmentTaskState;
import com.example.dmitry.a1c_client.domain.entity.Kit;

import java.util.List;

import rx.Observable;

/**
 * Created by Admin on 26.12.2016.
 */

public interface EquipmentTaskView {
    void showProgress();
    void hideProgress();
    Observable<CharSequence> getBarCodeObservable();
    void setAdapter(RecyclerView.Adapter adapter);
    void setViews(Kit kit);
    void showError(String message);
    void hideViews();
    void onComplete();

    interface Callback{
        void onEquipmentComplete();
    }
}
