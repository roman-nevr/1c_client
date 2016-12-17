package com.example.dmitry.a1c_client.presentation;


import com.example.dmitry.a1c_client.BuildConfig;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateDocumentsInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.IncomeState.State.notInitialased;
import static com.example.dmitry.a1c_client.domain.entity.IncomeState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeState.State.ready;

public class IncomePresenter {
    @Inject IncomeView view;
    @Inject StateKeeper<IncomeState> incomeStateKeeper;
    @Inject UpdateDocumentsInteractor updateDocumentsInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public IncomePresenter() {}

    public void init() {
        boolean success = incomeStateKeeper.change(state -> {
            if (state.state() == notInitialased) {
                return state.toBuilder().state(progress).build();
            }else {
                return null;
            }
        });
        if (success) {
            updateDocumentsInteractor.execute();
        }
    }

    public void start() {
        compositeSubscription = new CompositeSubscription();
        Observable<IncomeState> observable = incomeStateKeeper
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread());
        subscribeIncomeStateState(observable);
        subscribeDocuments(observable);

    }

    private void subscribeDocuments(Observable<IncomeState> observable) {
        Subscription subscription = observable
                .filter(incomeState -> incomeState.state()==ready)
                .map(IncomeState::documents)
                .distinctUntilChanged()
                .subscribe(
                        documents -> {
                            view.setDocuments(documents);
                        },
                        Throwable::printStackTrace
                );
        compositeSubscription.add(subscription);
    }

    private void subscribeIncomeStateState(Observable<IncomeState> observable) {
        Subscription subscription = observable
                .map(IncomeState::state)
                .distinctUntilChanged()
                .subscribe(
                        state -> {
                            switch (state) {
                                case progress:
                                    view.showProgress();
                                    view.hideDocuments();
                                    view.hideError();
                                    break;
                                case error:
                                    view.showError();
                                    view.hideDocuments();
                                    view.hideProgress();
                                    break;
                                case ready:
                                    view.showDocuments();
                                    view.hideProgress();
                                    view.hideError();
                                    break;
                                default:
                                    if (BuildConfig.DEBUG) {
                                        throw new UnsupportedOperationException();
                                    }
                            }

                        },
                        Throwable::printStackTrace
                );
        compositeSubscription.add(subscription);
    }

    public void stop() {
        compositeSubscription.unsubscribe();

    }
}
