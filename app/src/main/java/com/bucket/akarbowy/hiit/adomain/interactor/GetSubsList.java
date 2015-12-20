package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetSubsList extends UseCase {

    private final String mUserId;
    private final Repository mRepository;

    @Inject
    public GetSubsList(String userId, Repository repository) {
        mUserId = userId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getSubscriptions(mUserId);
    }
}
