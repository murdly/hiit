package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface EnrolledView extends BaseView {

    void adaptEventsList(List<EventModel> eventModelsList);

    void viewEvent(EventModel eventModel);

    void showViewRefreshing();

    void hideViewRefreshing();

    void hideViewEmpty();

    void showViewEmpty();

    void showError(String msg);

    Context getContext();

}
