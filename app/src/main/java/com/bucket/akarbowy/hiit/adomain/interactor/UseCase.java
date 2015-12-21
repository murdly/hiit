package com.bucket.akarbowy.hiit.adomain.interactor;


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


    protected abstract Observable buildUseCaseObservable(Object object);

    @SuppressWarnings("unchecked")
      public void execute(Subscriber subscriber) {
        mSubscription = this.buildUseCaseObservable(null)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    public void execute(Subscriber subscriber, Object object) {
        mSubscription = this.buildUseCaseObservable(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }

    @SuppressWarnings("unchecked")
    public void execute(Observer observer, Object object) {
        mSubscription = this.buildUseCaseObservable(object)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    public void unsubscribe(){
        if(!mSubscription.isUnsubscribed())
            mSubscription.unsubscribe();
    }

}
