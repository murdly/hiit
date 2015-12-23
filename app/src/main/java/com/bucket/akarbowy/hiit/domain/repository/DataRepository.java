package com.bucket.akarbowy.hiit.domain.repository;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.bucket.akarbowy.hiit.exception.NetworkConnectionException;
import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.Technology;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.parse.CountCallback;
import com.parse.FindCallback;
import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Created by akarbowy on 11.12.2015.
 */
public class DataRepository implements Repository {

    private Context mContext;

    @Inject
    public DataRepository(Context context) {
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
                    query.whereNotEqualTo("isCanceled", true); //todo zanegowac
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
    public Observable<List<Event>> getEnrolledEvents(final String userId) {
        return Observable.create(new Observable.OnSubscribe<List<Event>>() {
            @Override
            public void call(final Subscriber<? super List<Event>> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery()
                            .whereEqualTo("participants", userId)
                            .orderByAscending(Event.EVENT_COL_DATETIME)
                            .findInBackground(new FindCallback<Event>() {
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
                    Event.getQuery().include("author")
                            .getInBackground(eventId, new GetCallback<Event>() {
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
    public Observable<List<? super Technology>> getSubscriptions(final String userId) {
        return Observable.create(new Observable.OnSubscribe<List<? super Technology>>() {
            @Override
            public void call(final Subscriber<? super List<? super Technology>> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    ParseUser.getCurrentUser().getRelation("mysubs").getQuery()
                            .orderByAscending("title")
                            .findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> technologies, ParseException e) {
                                    if (e != null) subscriber.onError(e);
                                    else {
                                        subscriber.onNext(technologies);
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
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    try {
                        Technology technology = Technology.getQuery().get(model.getTechnologyId());
                        Event event = new Event();
                        event.setAuthor();
                        event.setTitle(model.getTitle());
                        event.setTechnology(technology);
                        event.setDateTime(model.getDateTimeInMillis());
                        event.setDescription(model.getDescription());
                        event.setLocalization(model.getLocalization());
                        save(event, subscriber);
                    } catch (ParseException e) {
                        subscriber.onError(e);
                    }

                }
            }
        });
    }

    @Override
    public Observable<Void> cancelEvent(final String eventId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery().getInBackground(eventId, new GetCallback<Event>() {
                        @Override
                        public void done(Event event, ParseException e) {
                            if (e != null) subscriber.onError(e);
                            else {
                                event.setCanceled(true);
                                save(event, subscriber);
                            }
                        }
                    });
                }

            }
        });
    }

    @Override
    public Observable<Void> updateEvent(final EventModel model) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery().getInBackground(model.getId(), new GetCallback<Event>() {
                        @Override
                        public void done(Event event, ParseException e) {
                            if (e != null) subscriber.onError(e);
                            else {
                                try {
                                    Technology technology = Technology.getQuery().get(model.getTechnologyId());
                                    event.setTitle(model.getTitle());
                                    event.setTechnology(technology);
                                    event.setDateTime(model.getDateTimeInMillis());
                                    event.setDescription(model.getDescription());
                                    event.setLocalization(model.getLocalization());
                                    save(event, subscriber);
                                } catch (ParseException e1) {
                                    subscriber.onError(e1);
                                }


                            }
                        }
                    });
                }
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
    public Observable<Void> enrollUser(final String eventId, final ParseUser user) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery().getInBackground(eventId, new GetCallback<Event>() {
                        @Override
                        public void done(final Event event, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                event.getParticipantsRelation().add(user);
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
            }
        });
    }

    @Override
    public Observable<Void> disenrollUser(final String eventId, final ParseUser user) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Event.getQuery().getInBackground(eventId, new GetCallback<Event>() {
                        @Override
                        public void done(final Event event, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                event.getParticipantsRelation().remove(user);
                                event.increment("participantsCounter", -1);
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
            }
        });
    }

    @Override
    public Observable<List<Technology>> findTechnology(final String userId, final String query) {
        return Observable.create(new Observable.OnSubscribe<List<Technology>>() {
            @Override
            public void call(final Subscriber<? super List<Technology>> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    ParseUser.getCurrentUser().getRelation("mysubs").getQuery()
                            .findInBackground(new FindCallback<ParseObject>() {
                                @Override
                                public void done(List<ParseObject> technologies, ParseException e) {
                                    if (e != null) {
                                        subscriber.onError(e);
                                    } else {
                                        List<String> techIds = new ArrayList<>();
                                        for (ParseObject tech : technologies)
                                            techIds.add(tech.getObjectId());

                                        Technology.getQuery()
                                                .whereStartsWith("title", query)
                                                .whereNotContainedIn("objectId", techIds)
                                                .findInBackground(new FindCallback<Technology>() {
                                                    @Override
                                                    public void done(List<Technology> results, ParseException e) {
                                                        if (e != null) {
                                                            subscriber.onError(e);
                                                        } else {
                                                            subscriber.onNext(results);
                                                            subscriber.onCompleted();
                                                        }
                                                    }
                                                });
                                    }
                                }
                            });

                }
            }
        });
    }

    @Override
    public Observable<Void> addSubscription(final ParseUser user, final String techId) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Technology.getQuery().getInBackground(techId, new GetCallback<Technology>() {
                        @Override
                        public void done(Technology technology, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                user.getRelation("mysubs").add(technology);
                                user.saveInBackground(new SaveCallback() {
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
            }
        });
    }

    @Override
    public Observable<Boolean> cancelSub(final ParseUser currentUser, final String techId) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(final Subscriber<? super Boolean> subscriber) {
                if (!isThereInternetConnection()) {
                    subscriber.onError(new NetworkConnectionException());
                } else {
                    Technology.getQuery().getInBackground(techId, new GetCallback<Technology>() {
                        @Override
                        public void done(Technology technology, ParseException e) {
                            if (e != null) {
                                subscriber.onError(e);
                            } else {
                                currentUser.getRelation("mysubs").remove(technology);
                                currentUser.saveInBackground(new SaveCallback() {
                                    @Override
                                    public void done(ParseException e) {
                                        if (e != null) {
                                            subscriber.onError(e);
                                        } else {
                                            currentUser.getRelation("mysubs").getQuery()
                                                    .countInBackground(new CountCallback() {
                                                        @Override
                                                        public void done(int count, ParseException e) {
                                                            if (e != null) subscriber.onError(e);
                                                            else {
                                                                subscriber.onNext(count == 0);
                                                                subscriber.onCompleted();
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
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
