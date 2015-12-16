package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class SaveEvent extends UseCase {

    private final EventRepository mEventRepository;

    @Inject
    public SaveEvent(EventRepository eventRepository) {
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable() {
        return null;
    }

    @Override
    protected Observable buildUseCaseObservable(Event event) {
        return mEventRepository.saveEvent(event);
    }
}
