package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class CancelEvent extends UseCase {

    private final String mEventId;
    private final EventRepository mEventRepository;

    @Inject
    public CancelEvent(String eventId, EventRepository eventRepository) {
        mEventId = eventId;
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mEventRepository.cancelEvent(mEventId);
    }
}
