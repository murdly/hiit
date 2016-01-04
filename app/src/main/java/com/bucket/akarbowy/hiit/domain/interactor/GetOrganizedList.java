package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetOrganizedList extends UseCase {

    private final ParseUser mUser;
    private final Repository mRepository;

    @Inject
    public GetOrganizedList(ParseUser user, Repository repository) {
        mUser = user;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getOrganizedEvents(mUser);
    }
}
