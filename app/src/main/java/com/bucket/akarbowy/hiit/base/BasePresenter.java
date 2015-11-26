package com.bucket.akarbowy.hiit.base;

/**
 * Created by akarbowy on 26.11.2015.
 */
public interface BasePresenter<T extends BaseView> {
    void attachView(T view);

    void detachView();
}
