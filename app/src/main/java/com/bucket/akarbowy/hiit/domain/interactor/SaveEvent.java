package com.bucket.akarbowy.hiit.domain.interactor;

import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.bucket.akarbowy.hiit.model.EventModel;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class SaveEvent extends UseCase {

    private final Repository mRepository;

    @Inject
    public SaveEvent(Repository repository) {
        mRepository = repository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object object) {
        EventModel event = (EventModel)object;

        return event.getId().isEmpty()
                ? mRepository.createEvent(event)
                : mRepository.updateEvent(event);
    }
}
