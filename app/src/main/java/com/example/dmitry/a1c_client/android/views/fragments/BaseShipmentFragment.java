package com.example.dmitry.a1c_client.android.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.android.interfaces.ShipmentTaskItemView.ShipmentViewCallback;
import com.example.dmitry.a1c_client.domain.entity.ShipmentTaskPosition;
import com.example.dmitry.a1c_client.misc.utils;
import com.example.dmitry.a1c_client.presentation.BaseShipmentPresenter;
import com.example.dmitry.a1c_client.presentation.ShipmentTaskView;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Admin on 27.12.2016.
 */

public abstract class BaseShipmentFragment extends Fragment
        implements ShipmentTaskView, ShipmentViewCallback, MessageCallBack, AnswerCallBack {

    @BindView(R.id.pbCollecting) ProgressBar taskProgressBar;
    @BindView(R.id.viewPager) ViewPager viewPager;
    @BindView(R.id.progressBar) ProgressBar progress;

    private ShipmentCallback callback;

    public static final int INSUFFICIENT_REPORT = 1;
    public static final int SHOW_ALL_DIALOG = 2;

    public static final int FINAL = 4;
    public static final String ID = "id";

    private String id;

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

    @Override
    public void initProgressBar(int start, int max) {
        taskProgressBar.setMax(max);
        taskProgressBar.incrementProgressBy(start);
    }

    @Override
    public void incProgress() {
        taskProgressBar.incrementProgressBy(1);
    }

    @Override
    public ViewPager getViewPager() {
        return viewPager;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getActivity() instanceof ShipmentCallback){
            callback = (ShipmentCallback) getActivity();
        }else {
            throw new UnsupportedOperationException("parent must implement ShipmentTaskView.Callback");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.shipment_act_fragment_layout, container, false);
        ButterKnife.bind(this, fragmentView);
        initDI();
        initPresenter();
        return fragmentView;
    }

    @Override
    public FragmentManager provideFragmentManager() {
        return getChildFragmentManager();
    }

    protected abstract void initDI();

    protected void initPresenter(){
        presenter().setView(this);
        presenter().init();
    }

    protected void showMessage(String message, int id) {
        MessageDialogFragment fragment = MessageDialogFragment.newInstance(message, id);
        fragment.show(getChildFragmentManager(), "end");
    }

    @Override
    public void onComplete() {
        //showMessage("Задание выполнено", FINAL);
        callback.onShipmentComplete();
    }

    @Override
    public void onMessageButtonClick(int id) {
        if(id == FINAL){
            getActivity().finish();
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.crossed_table_item: {
                showQuestion("Сформировать отчет о недостаче?", "Да", "Нет", INSUFFICIENT_REPORT);
                return true;
            }
            case R.id.warning_item: {
                showQuestion("Показать весь список?", "Да", "Нет", SHOW_ALL_DIALOG);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showQuestion(String question, String okButtonText, String cancelButtonText, int id){
        DialogFragment fragment = QuestionDialogFragment.newInstance(question, okButtonText,
                cancelButtonText, id);
        fragment.show(getChildFragmentManager(), "ask");
    }

    protected void hideKeyboard() {
        utils.hideKeyboard(getActivity());
    }

    protected abstract BaseShipmentPresenter presenter();

    @Override
    public void onStart() {
        super.onStart();
        presenter().start();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter().stop();
    }

    @Override
    public void onOkButtonClick(int queryId) {
        switch (queryId){
            case INSUFFICIENT_REPORT:{
                showMessage("Отчет отправлен", FINAL);
                //finish();
                break;
            }
            case SHOW_ALL_DIALOG:{
                presenter().showAllAccepted();
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onCancelButtonClick(int queryId) {
        switch (queryId) {
            case SHOW_ALL_DIALOG: {
                presenter().showAllDenied();
                break;
            }
            default: {
                break;
            }
        }
    }

    @Override
    public ShipmentTaskPosition getItem(int index) {
        return presenter().getPosition(index);
    }

    @Override
    public void onQuantityChanges(String id, int quantity) {
        presenter().onQuantityChanges(id, quantity);
    }

}
