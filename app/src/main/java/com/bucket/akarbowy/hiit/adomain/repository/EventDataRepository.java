package com.bucket.akarbowy.hiit.adomain.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bucket.akarbowy.hiit.adata.exception.NetworkConnectionException;
import com.bucket.akarbowy.hiit.adomain.Event;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by akarbowy on 11.12.2015.
 */
public class EventDataRepository implements EventRepository {

    private Context mContext;

    @Inject
    public EventDataRepository(Context context) {
        this.mContext = context;
    }

    @Override
    public Observable<List<Event>> rssEvents() {
        return Observable.create(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(final Subscriber<? super List<Event>> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    ParseQuery<Event> query = Event.getQuery();
                    query.orderByAscending(Event.EVENT_COL_DATETME);
                    query.findInBackground(new FindCallback<Event>() {
                        @Override
                        public void done(final List<Event> response, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                subscriber.onNext(response);
                                subscriber.onCompleted();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public Observable<Event> eventDetails() {
        return null;
    }

    private boolean isThereInternetConnection() {
        boolean isConnected;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        isConnected = (networkInfo != null && networkInfo.isConnectedOrConnecting());

        return isConnected;
    }
}
