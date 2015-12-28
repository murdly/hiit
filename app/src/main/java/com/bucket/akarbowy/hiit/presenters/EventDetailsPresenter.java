package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.base.BasePresenter;

/**
 * Created by akarbowy on 15.12.2015.
 */
public interface EventDetailsPresenter extends BasePresenter {
    void enrollUser();

    void disenrollUser();

    void cancelEvent();
}
