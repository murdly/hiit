package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.OwnEventsView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class OwnEventsPresenterImpl implements OwnEventsPresenter {

    private OwnEventsView mOwnEventsView;
    private UseCase mGetOwnEventsListUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    OwnEventsPresenterImpl(@Named("ownEventsList") UseCase getOwnEventsListUseCase, EventDataMapper eventDataMapper) {
        this.mGetOwnEventsListUseCase = getOwnEventsListUseCase;
        this.mEventDataMapper = eventDataMapper;
    }

    public void setView(OwnEventsView view) {
        mOwnEventsView = view;
    }

    public void initialize() {
        loadOwnEventsList();
    }


    private void loadOwnEventsList(){
        mOwnEventsView.hideViewEmpty();
        mOwnEventsView.showViewRefreshing();
        this.getOwnEventsList();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mOwnEventsView.viewEvent(eventModel);
    }

    private void showEventsInView(List<Event> events) {
        List<EventModel> eventModelsList = mEventDataMapper.transform(events);
        mOwnEventsView.adaptEventsList(eventModelsList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mOwnEventsView.getContext(), error);
        mOwnEventsView.showError(errorMessage);
    }

    public void getOwnEventsList() {
        mGetOwnEventsListUseCase.execute(new OwnEventsListSubscriber());
    }

    private final class OwnEventsListSubscriber extends DefaultSubscriber<List<Event>> {
        @Override
        public void onNext(List<Event> events) {
            if (events.isEmpty()) mOwnEventsView.showViewEmpty();
            else showEventsInView(events);
        }

        @Override
        public void onError(Throwable e) {
            mOwnEventsView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mOwnEventsView.hideViewRefreshing();
        }
    }

    @Override
    public void onDestroy() {
        mGetOwnEventsListUseCase.unsubscribe();
    }
}