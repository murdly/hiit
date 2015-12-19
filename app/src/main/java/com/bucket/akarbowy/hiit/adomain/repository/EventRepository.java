package com.bucket.akarbowy.hiit.adomain.repository;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.model.EventModel;

import java.util.List;

import rx.Observable;

/**
 * Created by akarbowy on 11.12.2015.
 */
public interface EventRepository {

    Observable<List<Event>> getRssEvents();

    Observable<Event> getEventDetails(String eventId);

    Observable<Void> createEvent(EventModel event);

    Observable<Void> enrollUser(String mEventId, String objectId);

    Observable<Void> updateEvent(EventModel event);
}
