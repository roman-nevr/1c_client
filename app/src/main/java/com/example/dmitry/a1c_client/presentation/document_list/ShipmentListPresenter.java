package com.example.dmitry.a1c_client.presentation.document_list;


import com.example.dmitry.a1c_client.BuildConfig;
import com.example.dmitry.a1c_client.domain.StateKeeper;
import com.example.dmitry.a1c_client.domain.entity.ShipmentListState;
import com.example.dmitry.a1c_client.domain.interactor.UpdateShipmentDocumentsInteractor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.notInitialased;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.progress;
import static com.example.dmitry.a1c_client.domain.entity.Enums.DocumentsState.ready;


public class ShipmentListPresenter {
    @Inject ShipmentListView view;
    @Inject StateKeeper<ShipmentListState> incomeStateKeeper;
    @Inject UpdateShipmentDocumentsInteractor updateDocumentsInteractor;
    private CompositeSubscription compositeSubscription;

    @Inject
    public ShipmentListPresenter() {
        compositeSubscription = new CompositeSubscription();
    }

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
                Observable<ShipmentListState> observable = incomeStateKeeper
                .getObservable()
                .observeOn(AndroidSchedulers.mainThread());
        subscribeIncomeStateState(observable);
        subscribeDocuments(observable);
    }

    private void subscribeDocuments(Observable<ShipmentListState> observable) {
        Subscription subscription = observable
                .filter(incomeState -> incomeState.state()==ready)
                .map(ShipmentListState::documents)
                .distinctUntilChanged()
                .subscribe(
                        documents -> {
                            view.setDocuments(documents);
                        },
                        Throwable::printStackTrace
                );
        compositeSubscription.add(subscription);
    }

    private void subscribeIncomeStateState(Observable<ShipmentListState> observable) {
        Subscription subscription = observable
                .map(ShipmentListState::state)
                .distinctUntilChanged()
                .subscribe(
                        state -> {
                            switch (state) {
                                case progress:
                                    view.showProgress();
                                    break;
                                case downloadError:
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
        compositeSubscription.clear();

    }

    public void update() {
        updateDocumentsInteractor.execute();
    }
}
