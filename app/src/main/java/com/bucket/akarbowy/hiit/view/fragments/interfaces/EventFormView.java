package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface EventFormView extends BaseView {
    boolean isValid();

    void showError(String msg);

    Context getContext();

    void showViewSaving();

    void hideViewSaving();

    void showViewWaiting();

    void hideViewWaiting();

    void close();

    void renderEvent(EventModel eventModel);

    EventModel getEventModel();

}
