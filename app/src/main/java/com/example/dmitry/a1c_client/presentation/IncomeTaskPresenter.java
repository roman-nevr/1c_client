package com.example.dmitry.a1c_client.presentation;

import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeTaskState;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by roma on 18.12.2016.
 */

public class IncomeTaskPresenter {
    @Inject IncomeTaskView view;
    @Inject StateKeeper<IncomeTaskState> stateKeeper;
    private CompositeSubscription subscriptions;

    @Inject
    public IncomeTaskPresenter(){}

    public void init(){}

}
