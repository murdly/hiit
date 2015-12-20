package com.bucket.akarbowy.hiit.adomain.repository;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.Technology;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.parse.ParseUser;

import java.util.List;

import rx.Observable;

/**
 * Created by akarbowy on 11.12.2015.
 */
public interface Repository {

    Observable<List<Event>> getRssEvents();

    Observable<List<Event>> getEnrolledEvents(String userId);

    Observable<Event> getEventDetails(String eventId);

    Observable<Void> createEvent(EventModel event);

    Observable<Void> enrollUser(String eventId, ParseUser user);

    Observable<Void> updateEvent(EventModel event);

    Observable<Void> disenrollUser(String eventId, ParseUser user);

    Observable<Void> cancelEvent(String eventId);

    Observable<List<Technology>> getSubscriptions(String userId);
}
