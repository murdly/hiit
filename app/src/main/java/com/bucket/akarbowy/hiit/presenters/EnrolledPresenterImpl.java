package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EnrolledView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class EnrolledPresenterImpl implements EnrolledPresenter {

    private EnrolledView mEnrolledView;
    private UseCase mGetEnrolledListUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    EnrolledPresenterImpl(@Named("enrolledList") UseCase getEnrolledListUseCase, EventDataMapper eventDataMapper) {
        this.mGetEnrolledListUseCase = getEnrolledListUseCase;
        this.mEventDataMapper = eventDataMapper;
    }

    public void setView(EnrolledView view) {
        mEnrolledView = view;
    }

    public void initialize() {
        loadEnrolledList();
    }


    private void loadEnrolledList() {
        mEnrolledView.hideViewEmpty();
        mEnrolledView.showViewRefreshing();
        this.getEnrolledList();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mEnrolledView.viewEvent(eventModel);
    }

    private void showEventsInView(List<Event> events) {
        List<EventModel> eventModelsList = mEventDataMapper.transform(events);
        mEnrolledView.adaptEventsList(eventModelsList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mEnrolledView.getContext(), error);
        mEnrolledView.showError(errorMessage);
    }

    public void getEnrolledList() {
        mGetEnrolledListUseCase.execute(new EnrolledListSubscriber());
    }

    private final class EnrolledListSubscriber extends DefaultSubscriber<List<Event>> {
        @Override
        public void onNext(List<Event> events) {
            if (events.isEmpty()) mEnrolledView.showViewEmpty();
            showEventsInView(events);
        }

        @Override
        public void onError(Throwable e) {
            mEnrolledView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mEnrolledView.hideViewRefreshing();
        }
    }
}