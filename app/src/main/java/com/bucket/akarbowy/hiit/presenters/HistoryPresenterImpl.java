package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.HistoryView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class HistoryPresenterImpl implements EnrolledPresenter {

    private HistoryView mHistoryView;
    private UseCase mGetHistoryParticipatedListUseCase, mGetHistoryOrganizedListUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    HistoryPresenterImpl(@Named("historyParticipatedList") UseCase getHistoryParticipatedListUseCase,
                         @Named("historyOrganizedList") UseCase getHistoryOrganizedListUseCase,
                         EventDataMapper eventDataMapper) {
        this.mGetHistoryParticipatedListUseCase = getHistoryParticipatedListUseCase;
        this.mGetHistoryOrganizedListUseCase = getHistoryOrganizedListUseCase;
        this.mEventDataMapper = eventDataMapper;
    }

    public void setView(HistoryView view) {
        mHistoryView = view;
    }


    public void loadParticipatedList(){
        mHistoryView.hideViewEmpty();
        mHistoryView.showViewRefreshing();
        this.getParticipatedList();
    }

    public void loadOrganizedList(){
        mHistoryView.hideViewEmpty();
        mHistoryView.showViewRefreshing();
        this.getOrganizedList();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mHistoryView.viewEvent(eventModel);
    }

    private void showEventsInView(List<Event> events) {
        List<EventModel> eventModelsList = mEventDataMapper.transform(events);
        mHistoryView.setEventsList(eventModelsList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mHistoryView.getContext(), error);
        mHistoryView.showError(errorMessage);
    }

    public void getParticipatedList() {
        mGetHistoryParticipatedListUseCase.execute(new HistoryListSubscriber());
    }

    public void getOrganizedList() {
        mGetHistoryOrganizedListUseCase.execute(new HistoryListSubscriber());
    }

    private final class HistoryListSubscriber extends DefaultSubscriber<List<Event>> {
        @Override
        public void onNext(List<Event> events) {
            if (events.isEmpty()) mHistoryView.showViewEmpty();
            else showEventsInView(events);
        }

        @Override
        public void onError(Throwable e) {
            mHistoryView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mHistoryView.hideViewRefreshing();
        }
    }
}