package com.bucket.akarbowy.hiit.model;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.di.PerActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by akarbowy on 10.12.2015.
 */
@PerActivity
public class EventDataMapper {

    @Inject
    public EventDataMapper() {
    }

    public EventModel transform(Event event) {
        if (event == null) throw new IllegalArgumentException("Cannot transform a null value");
        EventModel eventModel = new EventModel(event.getObjectId());
        eventModel.setAuthorId(event.getAuthorId());
        eventModel.setTitle(event.getTitle());
//        event.setTechnologyId("techId"); //todo tylko te co subskrybuj
        eventModel.setDateTime(event.getDateTimeInMillis());
        eventModel.setLocalization(event.getLocalization());
        eventModel.setDescription(event.getDescription());
        eventModel.setCanceled(event.isCanceled());
        return eventModel;
    }

    public List<EventModel> transform(List<Event> events) {
        List<EventModel> eventModelsList;

        if (events == null || events.isEmpty()) {
            eventModelsList = Collections.emptyList();
        } else {
            eventModelsList = new ArrayList<>();
            for (Event event : events) {
                eventModelsList.add(transform(event));
            }
        }

        return eventModelsList;
    }
}
