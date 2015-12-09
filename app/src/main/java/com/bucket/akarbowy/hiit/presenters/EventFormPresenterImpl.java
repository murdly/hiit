package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.UseCase;
import com.bucket.akarbowy.hiit.view.fragments.EventFormView;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class EventFormPresenterImpl implements EventFormPresenter {

    private String mEventId;

    private EventFormView mView;
    private UseCase mGetEventDetailsUseCase;

    @Inject
    EventFormPresenterImpl(@Named("eventDetails") UseCase getEventDetailsUseCase) {
        this.mGetEventDetailsUseCase = getEventDetailsUseCase;
    }

    public void setView(EventFormView view) {
        mView = view;
    }

    public void initialize(String eventId) {
        mEventId = eventId;
        if(!mEventId.isEmpty())
            loadDetails();
    }

    private void loadDetails() {
//        hideViewRetry();
//        showViewLoading();
       getEventDetails();
    }

    public void getEventDetails() {

    }

    @DebugLog
    @Override
    public void save() {
    }
}
