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

    public Event transform(EventModel eventModel) {
        Event event = new Event();
        event.setAuthor();
        event.setTitle(eventModel.getTitle());
//        event.setTechnology(eventModel.getTechnologyId()); // TODO: 10.12.2015  relacja
        event.setDateTime(eventModel.getDateTime().getTimeInMillis());
        event.setDescription(eventModel.getDescription());
        event.setLocalization(eventModel.getLocalization());
        return event;
    }
}
