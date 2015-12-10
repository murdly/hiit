package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.base.BasePresenter;
import com.bucket.akarbowy.hiit.model.EventModel;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface EventFormPresenter extends BasePresenter {


    void save(EventModel eventModel);
}
