package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.Repository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetRssList extends UseCase {

    private final Repository mRepository;

    @Inject
    public GetRssList(Repository repository) {
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        return mRepository.getRssEvents();
    }
}
