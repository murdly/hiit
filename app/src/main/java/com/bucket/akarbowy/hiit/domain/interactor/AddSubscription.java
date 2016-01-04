package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 22.12.2015.
 */
public class AddSubscription extends UseCase {
    private final ParseUser mUser;
    private final Repository mRepository;

    @Inject
    public AddSubscription(ParseUser user, Repository repository) {
        mUser = user;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object techId) {
        return mRepository.addSubscription(mUser, (String) techId);
    }
}
