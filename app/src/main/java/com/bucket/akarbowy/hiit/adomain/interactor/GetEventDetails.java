package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetEventDetails extends UseCase {

    private final String mEventId;
    private final EventRepository mEventRepository;

    @Inject
    public GetEventDetails(String eventId, EventRepository eventRepository) {
        mEventId = eventId;
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mEventRepository.eventDetails();
    }
}
