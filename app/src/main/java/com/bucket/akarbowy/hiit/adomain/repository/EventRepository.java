package com.bucket.akarbowy.hiit.adomain.repository;

import com.bucket.akarbowy.hiit.adomain.Event;

import java.util.List;

import rx.Observable;

/**
 * Created by akarbowy on 11.12.2015.
 */
public interface EventRepository {

    Observable<List<Event>> rssEvents();

    Observable<Event> eventDetails();
}
