package com.example.dmitry.a1c_client.android.views.income_task;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.BaseActivity;
import com.example.dmitry.a1c_client.android.MyApplication;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.views.fragments.NewBarCodeDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.android.views.fragments.ShowMapDialogFragment;
import com.example.dmitry.a1c_client.di.income_task.DaggerIncomeTaskViewComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskComponent;
import com.example.dmitry.a1c_client.di.income_task.IncomeTaskViewModule;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;
import com.example.dmitry.a1c_client.presentation.IncomeTaskPresenter;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import javax.inject.Inject;

import butterknife.BindView;

import static com.example.dmitry.a1c_client.misc.utils.hideKeyboard;

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

    public static final int RETRY = 0;
    public static final int BARCODE_NOT_FOUND = 1;
    public static final int NEW_BARCODE_DIALOG = 2;

    public static final String ID = "id";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.init(getIntent().getStringExtra(ID));
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
        etBarCode.setEnabled(false);
        etQuantity.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(View.GONE);
        etBarCode.setEnabled(true);
        etQuantity.setEnabled(true);
        System.out.println("hide progress");
    }

    @Override
    public void showPosition(NomenclaturePosition position) {
        etBarCode.setText(position.barCode());
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
    public void showStorageInfo(String quantity, String place, String element) {
        //hideKeyboard(this);
        etQuantity.setText(quantity);
        tvStorageElement.setText(element);
        tvStoragePlace.setText(place);
    }

    @Override public void hideStorageInfo() {

    }

    @Override
    public void showMap(StoreMapObject storeMapObject) {
        DialogFragment fragment = ShowMapDialogFragment.newInstance();
        fragment.show(getSupportFragmentManager(), "map");
    }

    @Override
    public void showBarCodeNotFoundDialog() {
        hideKeyboard(this);
        QuestionDialogFragment.newInstance("Позиция по штрихкоду не найдена \n Завести новый?",
                "Да, завести", "Нет, не надо", BARCODE_NOT_FOUND).show(getSupportFragmentManager()
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
        showStorageInfo("", "", "");
        unitsSpinner.setAdapter(null);
        /*if(unitsSpinner.getAdapter()!=null){
            System.out.println("adapter notifyDataSetChanged null");
            ((UnitSpinnerAdapter)unitsSpinner.getAdapter()).clearItems();
        }*/
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

    public static void start(Context context, String id){
        Intent intent = new Intent(context, IncomeTaskActivity.class);
        intent.putExtra(ID, id);
        context.startActivity(intent);
    }

    @Override
    public void onMessageButtonClick(int id) {
    }

    @Override
    public void onOkButtonClick(int queryId) {
        switch (queryId){
            case RETRY:{
                presenter.onRetryAccept();
                break;
            }
            case BARCODE_NOT_FOUND:{
                presenter.onBarCodeNotFoundDialogAccept();
                break;
            }
            case NEW_BARCODE_DIALOG:{
                break;
            }
            default:throw new  UnsupportedOperationException();
        }
    }

    @Override
    public void onCancelButtonClick(int queryId) {
        switch (queryId){
            case RETRY:{
                presenter.onRetryDecline();
                break;
            }
            case BARCODE_NOT_FOUND:{
                presenter.onBarCodeNotFoundDialogDecline();
                break;
            }
            case NEW_BARCODE_DIALOG:{
                presenter.onNewBarCodeDialogDecline();
                break;
            }
            default:throw new  UnsupportedOperationException();
        }
    }

    private void clearComponent(){
        ((MyApplication)getApplication()).clearIncomeTaskComponent();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearComponent();
    }
}
