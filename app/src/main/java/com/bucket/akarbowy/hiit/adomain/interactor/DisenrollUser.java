package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class DisenrollUser extends UseCase {

    private final Repository mRepository;
    private final String mEventId;

    @Inject
    public DisenrollUser(String eventId, Repository repository) {
        mEventId = eventId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object user) {
        return mRepository.disenrollUser(mEventId, (ParseUser) user);
    }
}
