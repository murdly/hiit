package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EnrollUser extends UseCase {

    private final Repository mRepository;
    private final String mEventId;

    @Inject
    public EnrollUser(String eventId, Repository repository) {
        mEventId = eventId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object user) {
        return mRepository.enrollUser(mEventId, (ParseUser) user);
    }
}
