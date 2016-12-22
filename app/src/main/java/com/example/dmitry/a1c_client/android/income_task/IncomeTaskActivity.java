package com.example.dmitry.a1c_client.android.income_task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.BaseActivity;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.adapters.UnitSpinnerAdapter;
import com.example.dmitry.a1c_client.android.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.fragments.NewBarCodeDialogFragment;
import com.example.dmitry.a1c_client.android.fragments.QuestionDialogFragment;
import com.example.dmitry.a1c_client.android.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.di.income_task.DaggerIncomeTaskViewComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskViewModule;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;
import com.example.dmitry.a1c_client.presentation.IncomeTaskPresenter;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.dmitry.a1c_client.presentation.IncomeTaskPresenter.ASK_NEW_BARCODE;
import static com.example.dmitry.a1c_client.presentation.IncomeTaskPresenter.RETRY;

/**
 * Created by Admin on 19.12.2016.
 */

public class IncomeTaskActivity extends BaseActivity implements IncomeTaskView, AnswerCallBack, MessageCallBack{
    @BindView(R.id.etBarCode) EditText etBarCode;
    @BindView(R.id.tvNomenklauraName) TextView tvNomenklatura;
    @BindView(R.id.ivPic) ImageView ivPic;
    @BindView(R.id.tvInfoVendorCodeName) TextView tvVendorCode;
    @BindView(R.id.tvInfoDescriptionName) TextView tvDescription;
    @BindView(R.id.etQuantity) EditText etQuantity;
    @BindView(R.id.unitsSpinner) Spinner unitsSpinner;
    @BindView(R.id.btnGetStoragePlace) Button btnGetStoragePlace;
    @BindView(R.id.btnShowMap) Button btnShowMap;
    @BindView(R.id.tvStorageElement) TextView tvStorageElement;
    @BindView(R.id.tvStoragePlace) TextView tvStoragePlace;
    @BindView(R.id.tvClear) TextView tvClear;
    @BindView(R.id.progressBar) ProgressBar progressBar;
    @Inject IncomeTaskPresenter presenter;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.startSubscriptions(etBarCode, etQuantity, unitsSpinner,
                btnShowMap, btnGetStoragePlace);
    }



    @Override
    protected void onPause() {
        super.onPause();
        presenter.stop();
    }

    @Override
    protected int provideLayoutId() {
        return R.layout.details_item_form_layout;
    }

    @Override
    protected void initDi(Bundle savedInstanceState) {
        IncomeTaskComponent taskComponent = ((MyApplication)getApplicationContext())
                .getIncomeTaskComponent();
        DaggerIncomeTaskViewComponent.builder().incomeTaskComponent(taskComponent)
                .incomeTaskViewModule(new IncomeTaskViewModule(this)).build().inject(this);
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
    public void showPosition(NomenclaturePosition position) {
        tvNomenklatura.setText(position.positionName());
        tvDescription.setText(position.description());
        tvVendorCode.setText(position.vendorCode());
        etQuantity.setText("");
        enableStorageViews();
        unitsSpinner.setAdapter(presenter.provideSpinnerAdapter(getApplicationContext()));
    }

    private void enableStorageViews(){
        etQuantity.setEnabled(true);
        unitsSpinner.setEnabled(true);
        btnGetStoragePlace.setEnabled(true);
        btnShowMap.setEnabled(true);
    }

    private void disableStorageViews(){
        etQuantity.setEnabled(false);
        unitsSpinner.setEnabled(false);
        btnGetStoragePlace.setEnabled(false);
        btnShowMap.setEnabled(false);
    }



    @Override
    public void showStorageInfo(String place, String element) {
        tvStorageElement.setText(element);
        tvStoragePlace.setText(place);
    }

    @Override public void hideStorageInfo() {

    }

    @Override
    public void showMap(StoreMapObject storeMapObject) {

    }

    @Override
    public void showPositionNotFoundDialog() {
        QuestionDialogFragment.newInstance("Позиция по штрихкоду не найдена \n Завести новый?",
                "Да, завести", "Нет, не надо", ASK_NEW_BARCODE, this).show(getSupportFragmentManager()
                , "notFound");
    }

    @Override
    public void showNewBarCodeDialog(String barCode) {
        hideProgress();
        NewBarCodeDialogFragment.newInstance(barCode)
                .show(getSupportFragmentManager(), "barCode");
    }

    @Override
    public void showNetErrorMessage() {
        hideProgress();
        MessageDialogFragment
                .newInstance("Нет связи с сервером\n Попробуйте заново", RETRY)
                .show(getSupportFragmentManager(), "error");
    }

    @Override
    public void showNoRightsMessage() {
        hideProgress();
        MessageDialogFragment
                .newInstance("У вас нет прав на эту операцию", RETRY)
                .show(getSupportFragmentManager(), "noRights");
    }

    @Override
    public void showEmptyState() {
        etBarCode.setText("");
        tvNomenklatura.setText("");
        tvDescription.setText("");
        tvVendorCode.setText("");
        etQuantity.setText("");
        showStorageInfo("", "");
        if(unitsSpinner.getAdapter()!=null){
            System.out.println("not null");
            ((UnitSpinnerAdapter)unitsSpinner.getAdapter()).clearItems();
        }
        disableStorageViews();
    }

    @Override
    public String getQuantity() {
        return etQuantity.getText().toString();
    }

    @Override
    public void showQuantityError() {
        etQuantity.setError("ошибка");
    }

    @Override
    public void clearBarCode() {
        etBarCode.setText("");
    }

    public static void start(Context context){
        Intent intent = new Intent(context, IncomeTaskActivity.class);
        context.startActivity(intent);
    }

    @Override
    public void onMessageButtonClick(int id) {
        presenter.onMessageButton(id);
    }

    @Override
    public void onOkButtonClick(int queryId) {
        presenter.onOkButton(queryId);
    }

    @Override
    public void onCancelButtonClick(int queryId) {
        presenter.onCancelButton(queryId);
    }
}
