package com.example.dmitry.a1c_client.domain;


import rx.Observable;
import rx.subjects.BehaviorSubject;

public class StateKeeper<T> {
    private final BehaviorSubject<T> subject = BehaviorSubject.create();
    private final Object subjectLock = new Object();
    private final T defaultValue;

    public StateKeeper(T state) {
        defaultValue = state;
    }

    public Observable<T> getObservable() {
        return subject;
    }

    public void update(T state) {
        synchronized (subjectLock) {
            subject.onNext(state);
        }
    }

    public boolean change(Modifier<T> modifier) {
        synchronized (subjectLock) {
            T newState;
            if (subject.hasValue()) {
                newState = modifier.modify(subject.getValue());
            } else {
                newState = modifier.modify(defaultValue);
            }

            if (newState == null) {
                return false;
            } else {
                subject.onNext(newState);
                return true;
            }
        }
    }

    public interface Modifier<T> {
        T modify(T state);
    }
}
