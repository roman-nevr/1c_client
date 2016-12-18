package com.example.dmitry.a1c_client.presentation;


import com.example.dmitry.a1c_client.BuildConfig;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.IncomeListState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateDocumentsInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.notInitialased;
import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.progress;
import static com.example.dmitry.a1c_client.domain.entity.IncomeListState.State.ready;

public class IncomeListPresenter {
    @Inject
    IncomeListView view;
    @Inject StateKeeper<IncomeListState> incomeStateKeeper;
    @Inject UpdateDocumentsInteractor updateDocumentsInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public IncomeListPresenter() {}

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
        Observable<IncomeListState> observable = incomeStateKeeper
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread());
        subscribeIncomeStateState(observable);
        subscribeDocuments(observable);

    }

    private void subscribeDocuments(Observable<IncomeListState> observable) {
        Subscription subscription = observable
                .filter(incomeState -> incomeState.state()==ready)
                .map(IncomeListState::documents)
                .distinctUntilChanged()
                .subscribe(
                        documents -> {
                            view.setDocuments(documents);
                        },
                        Throwable::printStackTrace
                );
        compositeSubscription.add(subscription);
    }

    private void subscribeIncomeStateState(Observable<IncomeListState> observable) {
        Subscription subscription = observable
                .map(IncomeListState::state)
                .distinctUntilChanged()
                .subscribe(
                        state -> {
                            switch (state) {
                                case progress:
                                    view.showProgress();
                                    break;
                                case error:
                                    view.showError();
                                    break;
                                case ready:
                                    view.showDocuments();
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
