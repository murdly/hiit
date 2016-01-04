package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface HistoryView extends BaseView {

    void setEventsList(List<EventModel> eventModelsList);

    void viewEvent(EventModel eventModel);

    void showViewRefreshing();

    void hideViewRefreshing();

    void hideViewEmpty();

    void showViewEmpty();

}
