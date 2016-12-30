package com.example.dmitry.a1c_client.android.views.equipment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.di.equipment.DaggerEquipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.equipment.EquipmentTaskComponent;
import com.example.dmitry.a1c_client.domain.entity.Kit;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskPresenter;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskView;
import com.jakewharton.rxbinding.widget.RxTextView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

import static android.view.View.*;
import static com.example.dmitry.a1c_client.presentation.EquipmentTaskPresenter.FINAL;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentTaskFragment extends Fragment implements EquipmentTaskView, MessageCallBack {
    @Inject EquipmentTaskPresenter presenter;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @BindView(R.id.etBarCode) EditText etBarCode;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.tvKitName) TextView tvKitName;
    @BindView(R.id.tvKitDescription) TextView tvKitDescription;
    @BindView(R.id.llHidable) LinearLayout llHidable;
    @BindView(R.id.tvContainsLabel) TextView tvContainsLabel;
    private EquipCallback callback;

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
        etBarCode.setVisibility(INVISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
        etBarCode.setVisibility(VISIBLE);
    }

    @Override
    public Observable<CharSequence> getBarCodeObservable() {
        return RxTextView.textChanges(etBarCode);
    }

    @Override
    public void setAdapter(RecyclerView.Adapter adapter) {
        recyclerView.setVisibility(VISIBLE);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void setViews(Kit kit) {
        tvContainsLabel.setVisibility(VISIBLE);
        llHidable.setVisibility(VISIBLE);
        tvKitName.setText(kit.kitName().positionName());
        tvKitDescription.setText(kit.kitName().description());
    }

    @Override
    public void showError(String message) {
        utils.snackBarShort(etBarCode, message);
    }

    @Override
    public void hideViews() {
        llHidable.setVisibility(INVISIBLE);
        tvContainsLabel.setVisibility(INVISIBLE);
        recyclerView.setVisibility(INVISIBLE);
    }

    @Override
    public void onComplete() {
        callback.onEquipmentComplete();
    }

    @Override
    public FragmentManager fragmentManager() {
        return getChildFragmentManager();
    }

    @Override
    public void showBarCodeHint(String barCode) {
        etBarCode.setHint(barCode);
    }

    @Override
    public void clearBarCode() {
        etBarCode.setText("");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof EquipCallback){
            this.callback = (EquipCallback) context;
        }else {
            throw  new UnsupportedOperationException("activity must implement Callback");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        initDI();
        presenter.setView(this);
        View fragmentView = inflater.inflate(R.layout.equip_act_fragment_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        return fragmentView;
    }

    private void initDI() {
        EquipmentTaskComponent component = ((MyApplication) getActivity()
                .getApplication()).getEquipmentTaskComponent();
        DaggerEquipmentTaskViewComponent.builder().equipmentTaskComponent(component).build().inject(this);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        //inflater.inflate(R.menu.activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        super.onOptionsItemSelected(item);
        return false;
    }

    @Override
    public void onMessageButtonClick(int id) {
        if(id == FINAL){
            callback.onEquipmentComplete();
        }
    }
}
