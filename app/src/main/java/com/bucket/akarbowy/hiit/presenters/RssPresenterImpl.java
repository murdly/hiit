package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.RssView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class RssPresenterImpl implements RssPresenter {

    private RssView mRssView;
    private UseCase mGetRssListUseCase;
    private EventDataMapper mEventDataMapper;

    @Inject
    RssPresenterImpl(@Named("rssList") UseCase getRssListUseCase, EventDataMapper eventDataMapper) {
        this.mGetRssListUseCase = getRssListUseCase;
        this.mEventDataMapper = eventDataMapper;
    }

    public void setView(RssView view) {
        mRssView = view;
    }

    public void initialize() {
        if (!hasSubscription()) mRssView.showViewEmptyNoSubs();
        else loadRssList();
    }

    private boolean hasSubscription() {
        return true;
    }

    private void loadRssList(){
        mRssView.hideViewEmpty();
        mRssView.hideViewEmptyNoSubs();
        mRssView.showViewWaiting();
        this.getRssList();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mRssView.viewEvent(eventModel);
    }

    @DebugLog
    private void showEventsInView(List<Event> events) {
        List<EventModel> eventModelsList = mEventDataMapper.transform(events);
        mRssView.adaptEventsList(eventModelsList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mRssView.getContext(), error);
        mRssView.showError(errorMessage);
    }

    public void getRssList() {
        mGetRssListUseCase.execute(new RssListSubscriber());
    }

    private final class RssListSubscriber extends DefaultSubscriber<List<Event>> {
        @Override
        public void onNext(List<Event> events) {
            if (events.isEmpty()) mRssView.showViewEmpty();
            else showEventsInView(events);
        }

        @Override
        public void onError(Throwable e) {
            mRssView.hideViewWaiting();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mRssView.hideViewWaiting();
        }
    }
}