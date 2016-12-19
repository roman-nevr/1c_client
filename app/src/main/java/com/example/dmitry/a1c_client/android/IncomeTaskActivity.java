package com.example.dmitry.a1c_client.android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.fragments.QuestionDialogFragment;
import com.example.dmitry.a1c_client.android.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.di.DaggerIncomeFragmentComponent;
import com.example.dmitry.a1c_client.di.DaggerIncomeTaskComponent;
import com.example.dmitry.a1c_client.di.IncomeListModule;
import com.example.dmitry.a1c_client.di.IncomeTaskModule;
import com.example.dmitry.a1c_client.di.MainComponent;
import com.example.dmitry.a1c_client.domain.entity.NomenclaturePosition;
import com.example.dmitry.a1c_client.domain.entity.StoreMapObject;
import com.example.dmitry.a1c_client.misc.CommonFilters;
import com.example.dmitry.a1c_client.presentation.IncomeTaskPresenter;
import com.example.dmitry.a1c_client.presentation.IncomeTaskView;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.dmitry.a1c_client.misc.CommonFilters.isValidNumber;

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
    public static final int NEW_BARCODE = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unitsSpinner.setAdapter(presenter.provideSpinnerAdapter(getApplicationContext()));
        presenter.startSubscriptions(etBarCode, etQuantity, unitsSpinner,
                btnGetStoragePlace, btnShowMap);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.stop();
    }

    @Override
    int provideLayoutId() {
        return R.layout.details_item_form_layout;
    }

    @Override
    protected void initDi(Bundle savedInstanceState) {
        if(((MyApplication)getApplicationContext()).getIncomeTaskComponent() != null){
            ((MyApplication)getApplicationContext()).getIncomeTaskComponent().inject(this);
        }else {
            ((MyApplication)getApplicationContext()).buildIncomeTaskComponent(this).inject(this);
        }
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
    }

    @Override
    public void showStorageInfo(String place, String element) {
        tvStorageElement.setText(element);
        tvStoragePlace.setText(place);
    }

    @Override
    public void showMap(StoreMapObject storeMapObject) {

    }

    @Override
    public void showPositionNotFoundDialog() {
        QuestionDialogFragment.newInstance("Позиция по штрихкоду не найдена \n Завести новый?",
                "Да, завести", "Нет, не надо", NEW_BARCODE, this).show(getSupportFragmentManager()
                , "ask");
    }

    @Override
    public void showNewBarCodeDialog() {
    }

    @Override
    public void showNetErrorMessage() {
        hideProgress();
        MessageDialogFragment.newInstance("Нет связи с сервером\n Попробуйте заново", RETRY);
    }

    @Override
    public void showNoRightsMessage() {
        hideProgress();
        MessageDialogFragment.newInstance("У вас нет прав на эту операцию", RETRY);
    }

    @Override
    public void showEmptyState() {
        etBarCode.setText("");
        showPosition(NomenclaturePosition.EMPTY);
        showStorageInfo("", "");
    }

    @Override
    public String getQuantity() {
        return etQuantity.getText().toString();
    }

    @Override
    public void showQuantityError() {
        etQuantity.setError("ошибка");
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
