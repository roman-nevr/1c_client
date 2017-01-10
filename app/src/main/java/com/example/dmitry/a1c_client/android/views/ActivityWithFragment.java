package com.example.dmitry.a1c_client.android.views;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.MessageDialogFragment.MessageCallBack;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment;
import com.example.dmitry.a1c_client.android.views.fragments.QuestionDialogFragment.AnswerCallBack;
import com.example.dmitry.a1c_client.presentation.WindowView;

import butterknife.BindView;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

/**
 * Created by Admin on 10.01.2017.
 */

public abstract class ActivityWithFragment extends BaseActivity implements WindowView,
        AnswerCallBack {
    @BindView(R.id.progressBar) public ProgressBar progressBar;
    @BindView(R.id.toolbar) public Toolbar toolbar;

    public static final int QUIT_DIALOG = 1;

    @Override
    protected int provideLayoutId() {
        return R.layout.frame_layout;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(title());
        }
    }

    protected abstract String title();


    @Override
    public void showProgress() {
        progressBar.setVisibility(VISIBLE);
    }

    @Override
    public void hideProgress() {
        progressBar.setVisibility(GONE);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        clearComponent();
    }

    protected abstract void clearComponent();

    @Override
    public FragmentManager provideFragmentManager() {
        return getSupportFragmentManager();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                if(true){
                    showQuestion("Документ не завершен \n" +
                            " Закрыть все равно?", "Да", "Нет", QUIT_DIALOG);
                }
                return true;            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void showQuestion(String question, String okButtonText, String cancelButtonText, int id){
        DialogFragment fragment = QuestionDialogFragment.newInstance(question, okButtonText,
                cancelButtonText, id);
        fragment.show(getSupportFragmentManager(), "ask");
    }

    @Override
    public void onOkButtonClick(int queryId) {
        switch (queryId){
            case QUIT_DIALOG:{
                clearComponent();
                finish();
                break;
            }
            default:{
                break;
            }
        }
    }

    @Override
    public void onCancelButtonClick(int queryId) {

    }
}
