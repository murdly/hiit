package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetEnrolledList extends UseCase {

    private final String mUserId;
    private final Repository mRepository;

    @Inject
    public GetEnrolledList(String userId, Repository repository) {
        mUserId = userId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getEnrolledEvents(mUserId);
    }
}
