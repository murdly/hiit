package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.base.BasePresenter;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface SubscriptionPresenter extends BasePresenter {

    void onUnsubscribe(String techId);
}
