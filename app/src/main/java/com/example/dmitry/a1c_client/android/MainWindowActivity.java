package com.example.dmitry.a1c_client.android;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.dmitry.a1c_client.R;

public class MainWindowActivity extends AppCompatActivity {

    public static final String INCOME_FRAGMENT = "IncomeFragment";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            IncomeListFragment incomeFragment = new IncomeListFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_container, incomeFragment, INCOME_FRAGMENT).commit();
        }
    }
}
