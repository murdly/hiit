package com.bucket.akarbowy.hiit.model;

import com.bucket.akarbowy.hiit.di.PerActivity;
import com.bucket.akarbowy.hiit.adomain.Event;

import javax.inject.Inject;

/**
 * Created by akarbowy on 10.12.2015.
 */
@PerActivity
public class EventDataMapper {

    @Inject
    public EventDataMapper(){}

    public Event transform(Event event) {

        return event;
    }
}
