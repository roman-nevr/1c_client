package com.example.dmitry.a1c_client.presentation;

import android.support.v4.app.FragmentManager;

/**
 * Created by Admin on 29.12.2016.
 */
public interface EquipmentView {
    FragmentManager provideFragmentManager();

    void showProgress();
    void hideProgress();
}
