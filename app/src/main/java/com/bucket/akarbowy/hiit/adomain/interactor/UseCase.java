package com.bucket.akarbowy.hiit.adomain.interactor;


import com.bucket.akarbowy.hiit.adomain.Event;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by akarbowy on 09.12.2015.
 */
public abstract class UseCase {

    private Subscription mSubscription = Subscriptions.empty();

    protected abstract Observable buildUseCaseObservable();

    protected abstract Observable buildUseCaseObservable(Event event);

    @SuppressWarnings("unchecked")
    public void execute(Subscriber subscriber) {
        mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    public void execute(Observer observer, Event event) {
        mSubscription = this.buildUseCaseObservable(event)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unsubscribe(){
        if(!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

}
