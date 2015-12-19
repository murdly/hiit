package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EnrollUser extends UseCase {

    private final EventRepository mEventRepository;
    private final String mEventId;

    @Inject
    public EnrollUser(String eventId, EventRepository eventRepository) {
        mEventId = eventId;
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object userId) {
        return mEventRepository.enrollUser(mEventId, (String) userId);
    }
}
