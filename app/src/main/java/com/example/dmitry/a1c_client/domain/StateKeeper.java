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
            System.out.println("check " + state);
            subject.onNext(state);
        }
    }

    //returns true if onNext was called
    public boolean change(Modifier<T> modifier) {
        synchronized (subjectLock) {
            T newState;
            if (subject.hasValue()) {
                //System.out.println(subject.getStateKeeperValue());
                newState = modifier.modify(subject.getValue());
            } else {
                newState = modifier.modify(defaultValue);
            }

            if (newState == null) {
                return false;
            } else {
                System.out.println(newState);
                subject.onNext(newState);
                return true;
            }
        }
    }

    public T getValue(){
        return subject.getValue();
    }

    public interface Modifier<T> {
        T modify(T state);
    }
}
