package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.User;
import com.bucket.akarbowy.hiit.domain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetParticipatedList extends UseCase {

    private final String mUserId;
    private final Repository mRepository;

    @Inject
    public GetParticipatedList(String userId, Repository repository) {
        mUserId = userId;
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getParticipatedEvents(User.getCurrentUser());
    }
}
