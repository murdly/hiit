package com.bucket.akarbowy.hiit.domain.interactor;

import rx.Observer;

/**
 * Created by akarbowy on 11.12.2015.
 */
public abstract class NoEmittingObserver<T> implements Observer<T> {
    @Override
    public void onNext(T o) {

    }
}
