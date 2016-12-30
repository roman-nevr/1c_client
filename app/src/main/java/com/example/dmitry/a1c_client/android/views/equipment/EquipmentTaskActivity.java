package com.example.dmitry.a1c_client.android.views.equipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.BaseActivity;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.di.equipment.DaggerEquipViewComponent;
import com.example.dmitry.a1c_client.di.equipment.EquipViewModule;
import com.example.dmitry.a1c_client.di.equipment.EquipmentTaskComponent;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskActivityPresenter;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskView.EquipCallback;
import com.example.dmitry.a1c_client.presentation.EquipmentView;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView.ShipmentCallback;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.dmitry.a1c_client.presentation.EquipmentTaskActivityPresenter.EQUIP_STAGE;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentTaskActivity extends BaseActivity implements EquipmentView,
        EquipCallback, ShipmentCallback, MessageCallBack {

    @Inject EquipmentTaskActivityPresenter presenter;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    @Override
    protected int provideLayoutId() {
        return R.layout.frame_layout;
    }

    @Override
    protected void initDi(Bundle savedInstanceState) {
        EquipmentTaskComponent component = ((MyApplication)getApplication())
                .getEquipmentTaskComponent();
        DaggerEquipViewComponent.builder()
                .equipmentTaskComponent(component)
                .equipViewModule(new EquipViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.init(savedInstanceState);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearComponent();
    }

    @Override
    public void onEquipmentComplete() {
        clearComponent();
        finish();
    }

    private void clearComponent(){
        ((MyApplication)getApplication()).clearEquipmentTaskComponent();
    }

    @Override
    public void onShipmentComplete() {
        presenter.onShipmentComplete();
    }

    public static void start(Context context){
        Intent intent = new Intent(context, EquipmentTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onMessageButtonClick(int id) {
        if(id == EQUIP_STAGE){
            presenter.prepareEquipStage();
        }
    }
}
