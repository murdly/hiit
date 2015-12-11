package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 09.12.2015.
 */
public class GetRssList extends UseCase {

    private final EventRepository mEventRepository;

    @Inject
    public GetRssList(EventRepository eventRepository) {
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return mEventRepository.rssEvents();
    }
}
