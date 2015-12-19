package com.bucket.akarbowy.hiit.adomain.interactor;

import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;
import com.parse.ParseUser;

import javax.inject.Inject;

import rx.Observable;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class DisenrollUser extends UseCase {

    private final EventRepository mEventRepository;
    private final String mEventId;

    @Inject
    public DisenrollUser(String eventId, EventRepository eventRepository) {
        mEventId = eventId;
        mEventRepository = eventRepository;
    }

    @Override
    protected Observable buildUseCaseObservable(Object user) {
        return mEventRepository.disenrollUser(mEventId, (ParseUser) user);
    }
}
