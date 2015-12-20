package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetEnrolledList extends UseCase {

    private final String mUserId;
    private final EventRepository mEventRepository;

    @Inject
    public GetEnrolledList(String userId, EventRepository eventRepository) {
        mUserId = userId;
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mEventRepository.getEnrolledEvents(mUserId);
    }
}
