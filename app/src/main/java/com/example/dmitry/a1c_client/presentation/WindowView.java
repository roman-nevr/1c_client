package com.example.dmitry.a1c_client.presentation;

import android.support.v4.app.FragmentManager;

/**
 * Created by Admin on 09.01.2017.
 */
public interface WindowView {
    void showProgress();
    void hideProgress();
    FragmentManager provideFragmentManager();
}
