package com.bucket.akarbowy.hiit.presenters;

import android.util.Log;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.interactor.NoEmittingSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.exception.FormNotValidException;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventFormView;
import com.parse.ParseException;
import com.parse.SaveCallback;

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
                                  @Named("saveEvent") UseCase saveEventUseCase,
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
//        hideViewRetry();
//        showViewLoading();
        getEventDetails();
    }

    public void getEventDetails() {

    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mFormView.getContext(), error);
        mFormView.showError(errorMessage);
    }

    @DebugLog
    @Override
    public void save(Event event) {
        if (!mFormView.isValid()) {
            showErrorMessage(new FormNotValidException());
        } else {
            mFormView.showViewWaiting();
            mSaveEventUseCase.execute(new SaveEventObserver(), event);
        }
//        if (!mFormView.isValid()) {
//            showErrorMessage(new FormNotValidException());
//        } else if (!ConnectionUtil.isThereInternetConnection(mFormView.getContext())) {
//            showErrorMessage(new NetworkConnectionException());
//        } else {
//            mFormView.showViewWaiting();
//            event.saveInBackground(mOnDone);
//        }
    }

    private SaveCallback mOnDone = new SaveCallback() {
        @Override
        public void done(ParseException e) {
            mFormView.hideViewWaiting();
            if (e != null) showErrorMessage(e);
            else mFormView.close();
        }
    };

    private final class SaveEventObserver extends NoEmittingSubscriber<Void> {
        @Override
        public void onCompleted() {
            Log.d("EventormPresenter", "Sdsd");
            mFormView.hideViewWaiting();
            mFormView.close();
        }

        @Override
        public void onError(Throwable e) {
            mFormView.hideViewWaiting();
            showErrorMessage((Exception) e);
        }
    }

}