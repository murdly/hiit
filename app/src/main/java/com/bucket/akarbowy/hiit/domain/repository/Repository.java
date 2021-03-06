package com.bucket.akarbowy.hiit.domain.repository;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.Technology;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.parse.ParseUser;

import java.util.List;

import rx.Observable;

/**
 * Created by akarbowy on 11.12.2015.
 */
public interface Repository {

    Observable<List<Event>> getRssEvents();

    Observable<List<Event>> getEnrolledEvents(ParseUser user);

    Observable<Event> getEventDetails(String eventId);

    Observable<Void> createEvent(EventModel event);

    Observable<Void> enrollUser(String eventId, ParseUser user);

    Observable<Void> updateEvent(EventModel event);

    Observable<Void> disenrollUser(String eventId, ParseUser user);

    Observable<Void> cancelEvent(String eventId);

    Observable<List<? super Technology>> getSubscriptions(ParseUser user);

    Observable<List<Technology>> findTechnology(ParseUser user, String query);

    Observable<Void> addSubscription(ParseUser user, String techId);

    Observable<Boolean> cancelSub(ParseUser user, String techId);

    Observable<List<Event>> getOwnEvents(ParseUser user);

    Observable<List<Event>> getParticipatedEvents(ParseUser user);

    Observable<List<Event>> getOrganizedEvents(ParseUser user);
}
