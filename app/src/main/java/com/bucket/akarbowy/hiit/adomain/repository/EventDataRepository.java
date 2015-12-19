package com.bucket.akarbowy.hiit.adomain.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bucket.akarbowy.hiit.adata.exception.NetworkConnectionException;
import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

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
    public Observable<List<Event>> getRssEvents() {
        return Observable.create(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(final Subscriber<? super List<Event>> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    ParseQuery<Event> query = Event.getQuery();
                    query.orderByAscending(Event.EVENT_COL_DATETIME);
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
    public Observable<Event> getEventDetails(final String eventId) {
        return Observable.create(new Observable.OnSubscribe<Event>() {
            @Override
            public void call(final Subscriber<? super Event> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery().getInBackground(eventId, new GetCallback<Event>() {
                        @Override
                        public void done(Event event, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                subscriber.onNext(event);
                                subscriber.onCompleted();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public Observable<Void> createEvent(final EventModel model) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                Event event = new Event();
                event.setAuthor();
                event.setTitle(model.getTitle());
                event.setDateTime(model.getDateTimeInMillis());
                event.setDescription(model.getDescription());
                event.setLocalization(model.getLocalization());
                save(event, subscriber);
            }
        });
    }

    @Override
    public Observable<Void> updateEvent(final EventModel model) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                Event.getQuery().getInBackground(model.getId(), new GetCallback<Event>() {
                    @Override
                    public void done(Event event, ParseException e) {
                        if (e != null) subscriber.onError(e);
                        else {
                            event.setTitle(model.getTitle());
                            event.setDateTime(model.getDateTimeInMillis());
                            event.setDescription(model.getDescription());
                            event.setLocalization(model.getLocalization());
                            save(event, subscriber);
                        }
                    }
                });
            }
        });
    }

    private void save(Event event, final Subscriber<? super Void> subscriber) {
        event.saveInBackground(new SaveCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null) subscriber.onError(e);
                else subscriber.onCompleted();
            }
        });
    }

    @Override
    public Observable<Void> enrollUser(final String eventId, final String userId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                Event.getQuery().getInBackground(eventId, new GetCallback<Event>() {
                    @Override
                    public void done(final Event event, ParseException e) {
                        if (e != null) {
                            subscriber.onError(e);
                        } else {
                            event.getParticipantsRelation().add(ParseUser.getCurrentUser());
                            event.increment("participantsCounter");
                            event.saveInBackground(new SaveCallback() {
                                @Override
                                public void done(ParseException e) {
                                    if (e != null) {
                                        subscriber.onError(e);
                                    } else {
                                        subscriber.onCompleted();
                                    }
                                }
                            });
                        }
                    }
                });
            }
        });
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
