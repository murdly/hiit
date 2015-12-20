package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class CancelEvent extends UseCase {

    private final String mEventId;
    private final Repository mRepository;

    @Inject
    public CancelEvent(String eventId, Repository repository) {
        mEventId = eventId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.cancelEvent(mEventId);
    }
}
