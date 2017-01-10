package com.example.dmitry.a1c_client.android.views.equipment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.ActivityWithFragment;
import com.example.dmitry.a1c_client.android.views.BaseActivity;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.di.equipment.DaggerEquipViewComponent;
import com.example.dmitry.a1c_client.di.equipment.EquipViewModule;
import com.example.dmitry.a1c_client.di.equipment.EquipmentTaskComponent;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskActivityPresenter;
import com.example.dmitry.a1c_client.presentation.EquipmentTaskView.EquipCallback;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView.ShipmentCallback;
import com.example.dmitry.a1c_client.presentation.WindowView;

import javax.inject.Inject;

import butterknife.BindView;

import static android.view.View.*;
import static com.example.dmitry.a1c_client.presentation.EquipmentTaskActivityPresenter.EQUIP_STAGE;

/**
 * Created by Admin on 26.12.2016.
 */

public class EquipmentTaskActivity extends ActivityWithFragment implements
        EquipCallback, ShipmentCallback, MessageCallBack {

    @Inject EquipmentTaskActivityPresenter presenter;


    public static final String ID = "id";

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
        presenter.init(savedInstanceState, getIntent().getStringExtra(ID));
    }

    @Override
    protected String title() {
        return "Комлектация";
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
    public void onEquipmentComplete() {
        clearComponent();
        finish();
    }

    protected void clearComponent(){
        ((MyApplication)getApplication()).clearEquipmentTaskComponent();
    }

    @Override
    public void onShipmentComplete() {
        presenter.onShipmentComplete();
    }

    public static void start(Context context, String id){
        Intent intent = new Intent(context, EquipmentTaskActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onMessageButtonClick(int id) {
        if(id == EQUIP_STAGE){
            presenter.prepareEquipStage();
        }
    }
}
