package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetEventDetails extends UseCase {

    private final String mEventId;
    private final Repository mRepository;

    @Inject
    public GetEventDetails(String eventId, Repository repository) {
        mEventId = eventId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getEventDetails(mEventId);
    }
}
