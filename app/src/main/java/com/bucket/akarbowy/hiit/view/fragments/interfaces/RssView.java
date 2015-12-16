package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface RssView extends BaseView {

    void adaptEventsList(List<EventModel> eventModelsList);

    void viewEvent(EventModel eventModel);

    void showViewWaiting();

    void hideViewWaiting();

    void hideViewEmpty();

    void showViewEmpty();

    void hideViewEmptyNoSubs();

    void showViewEmptyNoSubs();

    void showError(String msg);

    Context getContext();

}
