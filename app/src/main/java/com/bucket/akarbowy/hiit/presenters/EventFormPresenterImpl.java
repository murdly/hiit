package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.NoEmittingObserver;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.exception.FormNotValidException;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventFormView;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class EventFormPresenterImpl implements EventFormPresenter {

    private String mEventId;

    private EventFormView mFormView;
    private UseCase mGetEventDetailsUseCase, mSaveEventUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    public EventFormPresenterImpl(@Named("eventDetails") UseCase getEventDetailsUseCase,
                                  @Named("createEvent") UseCase saveEventUseCase,
                                  EventDataMapper eventDataMapper) {
        this.mGetEventDetailsUseCase = getEventDetailsUseCase;
        this.mSaveEventUseCase = saveEventUseCase;
        this.mEventDataMapper = eventDataMapper;
    }

    public void setView(EventFormView view) {
        mFormView = view;
    }

    @DebugLog
    public void initialize(String eventId) {
        mEventId = eventId;
        if (!mEventId.isEmpty())
            loadDetails();
    }

    private void loadDetails() {
        mFormView.showViewWaiting();
        getEventDetails();
    }

    public void getEventDetails() {
        mGetEventDetailsUseCase.execute(new EventDetailsSubscriber());
    }

    private void showEventDetailsInView(Event event) {
        EventModel eventModel = mEventDataMapper.transform(event);
        mFormView.renderEvent(eventModel);

    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mFormView.getContext(), error);
        mFormView.showError(errorMessage);
    }

    @DebugLog
    @Override
    public void save() {
        if (!mFormView.isValid()) {
            showErrorMessage(new FormNotValidException());
        } else {
            mFormView.showViewSaving();
            mSaveEventUseCase.execute(new SaveEventObserver(), mFormView.getEventModel());
        }
    }

    private final class SaveEventObserver extends NoEmittingObserver<Void> {
        @Override
        public void onCompleted() {
            mFormView.hideViewSaving();
            mFormView.close();
        }

        @Override
        public void onError(Throwable e) {
            mFormView.hideViewSaving();
            showErrorMessage((Exception) e);
        }
    }

    private final class EventDetailsSubscriber extends DefaultSubscriber<Event> {
        @Override
        public void onCompleted() {
            mFormView.hideViewWaiting();
        }

        @Override
        public void onError(Throwable e) {
            mFormView.hideViewWaiting();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onNext(Event event) {
            showEventDetailsInView(event);
        }
    }

    @Override
    public void onDestroy() {
        mGetEventDetailsUseCase.unsubscribe();
        mSaveEventUseCase.unsubscribe();
    }
}