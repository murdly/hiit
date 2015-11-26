package com.bucket.akarbowy.hiit.base;

import java.lang.ref.WeakReference;

/**
 * Created by akarbowy on 26.11.2015.
 */
public class BasePresenterImpl<T extends BaseView> implements BasePresenter<T> {

    private WeakReference<T> mView;

    @Override
    public void attachView(T view) {
        this.mView = new WeakReference<T>(view);
        onViewAttached();
    }

    public T getView(){
        return mView != null ? mView.get() : null;
    }

    public boolean isViewAttached(){
        return mView != null && mView.get() != null;
    }
    @Override
    public void detachView() {
        if (mView != null) {
            mView.clear();
            mView = null;
            onViewDetached();
        }
    }

    public void onViewAttached() {
    }

    public void onViewDetached() {
    }

}
