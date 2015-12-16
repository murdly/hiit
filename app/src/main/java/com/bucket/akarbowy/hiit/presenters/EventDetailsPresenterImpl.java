package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.EventDetailsFragment;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventDetailsView;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 15.12.2015.
 */
public class EventDetailsPresenterImpl implements EventDetailsPresenter {

    private String mEventId;
    private EventDetailsView mEventDetailsView;
    private UseCase mGetEventDetailsUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    EventDetailsPresenterImpl(@Named("eventDetails") UseCase getEventDetailsUseCase, EventDataMapper eventDataMapper) {
        mGetEventDetailsUseCase = getEventDetailsUseCase;
        mEventDataMapper = eventDataMapper;
    }

    public void setView(EventDetailsFragment eventDetailsView) {
        mEventDetailsView = eventDetailsView;
    }

    public void initialize(String eventId) {
        mEventId = eventId;
        loadDetails();
    }

    private void loadDetails() {
        mEventDetailsView.showViewWaiting();
        getEventDetails();
    }

    private void showEventDetailsInView(Event event) {
        EventModel eventModel = mEventDataMapper.transform(event);
        mEventDetailsView.renderEvent(eventModel);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mEventDetailsView.getContext(), error);
        mEventDetailsView.showError(errorMessage);
    }

    private void getEventDetails() {
        mGetEventDetailsUseCase.execute(new EventDetailsSubscriber());
    }

    private final class EventDetailsSubscriber extends DefaultSubscriber<Event> {
        @Override
        public void onCompleted() {
            mEventDetailsView.hideViewWaiting();
        }

        @Override
        public void onError(Throwable e) {
            mEventDetailsView.hideViewWaiting();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onNext(Event event) {
            showEventDetailsInView(event);
        }
    }
}
