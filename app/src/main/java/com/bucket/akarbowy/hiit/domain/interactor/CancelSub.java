package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class CancelSub extends UseCase {

    private final Repository mRepository;
    private final String mUserId;

    @Inject
    public CancelSub(String eventId, Repository repository) {
        mUserId = eventId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object techId) {
        return mRepository.cancelSub(ParseUser.getCurrentUser(), (String) techId);
    }
}
