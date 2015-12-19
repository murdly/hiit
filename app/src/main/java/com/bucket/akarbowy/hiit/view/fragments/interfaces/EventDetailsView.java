package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.EventModel;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface EventDetailsView extends BaseView {

    void showError(String msg);

    Context getContext();

    void showViewWaiting();

    void hideViewWaiting();

    void renderEvent(EventModel eventModel);

    void inflateMenu(int menuId);

    void setEnrollmentIndicatorsActive(boolean enrolled);
}
