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
    private final ParseUser mUser;

    @Inject

    public CancelSub(ParseUser user, Repository repository) {
        mUser = user;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object techId) {
        return mRepository.cancelSub(mUser, (String) techId);
    }
}
