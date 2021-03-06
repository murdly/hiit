package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.RssView;
import com.parse.CountCallback;
import com.parse.ParseException;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

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
        mRssView.hideViewEmpty();
        mRssView.hideViewEmptyNoSubs();
        mRssView.showViewRefreshing();
        loadRssListIfHasSubs();
    }

    private void loadRssListIfHasSubs() {
        ParseUser.getSubsRelation().getQuery().countInBackground(new CountCallback() {
            @Override
            public void done(int count, ParseException e) {
                mRssView.hideViewRefreshing();

                if (e != null) showErrorMessage(e);
                else if (count > 0) {
                    getRssList();
                } else mRssView.showViewEmptyNoSubs();
            }
        });
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mRssView.viewEvent(eventModel);
    }

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
            else  showEventsInView(events);
        }

        @Override
        public void onError(Throwable e) {
            mRssView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mRssView.hideViewRefreshing();
        }
    }

    @Override
    public void onDestroy() {
        mGetRssListUseCase.unsubscribe();
    }
}