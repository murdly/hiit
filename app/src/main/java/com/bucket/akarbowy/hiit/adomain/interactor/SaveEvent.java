package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;
import com.bucket.akarbowy.hiit.model.EventModel;

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
    protected Observable buildUseCaseObservable(Object object) {
        EventModel event = (EventModel)object;

        return event.getId().isEmpty()
                ? mEventRepository.createEvent(event)
                : mEventRepository.updateEvent(event);
    }
}
