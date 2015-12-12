package com.bucket.akarbowy.hiit.view.fragments;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface RssView extends BaseView {

    void showError(String msg);

    Context getContext();

    void showViewWaiting();

    void hideViewWaiting();

    void adaptEventsList(List<EventModel> eventModelsList);
}
