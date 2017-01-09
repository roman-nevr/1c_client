package com.example.dmitry.a1c_client.android.views.shipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.BaseActivity;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentTaskViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.DaggerShipmentViewComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentTaskComponent;
import com.example.dmitry.a1c_client.di.shipment_task.ShipmentViewModule;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskActivityPresenter;
import com.example.dmitry.a1c_client.presentation.ShipmentView;

import javax.inject.Inject;

import butterknife.BindView;

import static android.view.View.*;
import static android.view.View.GONE;
import static com.example.dmitry.a1c_client.presentation.ShipmentTaskView.ShipmentCallback;

/**
 * Created by Admin on 23.12.2016.
 */

public class ShipmentTaskActivity extends BaseActivity implements ShipmentView,
        ShipmentCallback, MessageCallBack {

    @Inject ShipmentTaskActivityPresenter presenter;
    @BindView(R.id.progressBar) ProgressBar progressBar;

    public static final int FINAL = 4;
    public static final String ID = "id";

    public static void start(Context context, String id) {
        Intent intent = new Intent(context, ShipmentTaskActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }


    @Override
    protected int provideLayoutId() {
        return R.layout.frame_layout;
    }

    @Override
    protected void initDi(Bundle savedInstanceState) {
        super.initDi(savedInstanceState);
        ShipmentTaskComponent component = ((MyApplication)getApplication())
                .getShipmentTaskComponent();
        DaggerShipmentViewComponent.builder()
                .shipmentTaskComponent(component)
                .shipmentViewModule(new ShipmentViewModule(this))
                .build()
                .inject(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.init(savedInstanceState, getIntent().getStringExtra(ID));
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
    public void onShipmentComplete() {
        showMessage("Задание выполнено \nНажмите Ок для завершения", FINAL);
    }

    private void showMessage(String message, int id) {
        DialogFragment fragment = MessageDialogFragment.newInstance(message, id);
        fragment.show(getSupportFragmentManager(), "show");
    }


    @Override
    public void onMessageButtonClick(int id) {
        if (id == FINAL) {
            clearComponent();
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearComponent();
    }

    private void clearComponent() {
        ((MyApplication) getApplication()).clearShipmentTaskComponent();
    }

    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return getSupportFragmentManager();
    }
}
