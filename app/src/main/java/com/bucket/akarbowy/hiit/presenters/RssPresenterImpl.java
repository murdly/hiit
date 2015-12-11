package com.bucket.akarbowy.hiit.presenters;

import android.util.Log;

import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.adomain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.model.EventDataMapper;
import com.bucket.akarbowy.hiit.view.fragments.RssView;

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

    @DebugLog
    public void initialize() {
        mRssView.showViewWaiting();
        this.getRssList();
    }

    public void getRssList() {
        mGetRssListUseCase.execute(new RssListSubscriber());
    }

    private final class RssListSubscriber extends DefaultSubscriber<List<Event>>{
        @Override
        public void onNext(List<Event> events) {
            events.isEmpty();
        }

        @Override
        public void onError(Throwable e) {
        }

        @Override
        public void onCompleted() {
            Log.d("Rss", "complete");
        }
    }

//    private void showErrorMessage(Exception error) {
//        String errorMessage = ErrorMessageFactory.create(mRssView.getContext(), error);
//        mRssView.showError(errorMessage);
//    }
//
//    @DebugLog
//    @Override
//    public void save(EventModel eventModel) {
//        if (!mRssView.isValid()) {
//            showErrorMessage(new FormNotValidException());
//        } else if (!ConnectionUtil.isThereInternetConnection(mRssView.getContext())) {
//            showErrorMessage(new NetworkConnectionException());
//        } else {
//            mRssView.showViewWaiting();
//            Event event = mEventDataMapper.transform(eventModel);
//            event.saveInBackground(mOnDone);
//        }
//    }
}