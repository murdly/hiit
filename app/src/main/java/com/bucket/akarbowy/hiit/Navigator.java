package com.bucket.akarbowy.hiit;

import android.content.Context;
import android.content.Intent;

import com.bucket.akarbowy.hiit.view.activities.EventDetailsActivity;
import com.bucket.akarbowy.hiit.view.activities.EventFormActivity;
import com.bucket.akarbowy.hiit.view.activities.HistoryActivity;
import com.bucket.akarbowy.hiit.view.activities.LogInActivity;
import com.bucket.akarbowy.hiit.view.activities.MainActivity;
import com.bucket.akarbowy.hiit.view.activities.NotificationsActivity;
import com.bucket.akarbowy.hiit.view.activities.OwnEventsActivity;
import com.bucket.akarbowy.hiit.view.activities.SearchActivity;
import com.bucket.akarbowy.hiit.view.activities.SignUpActivity;
import com.bucket.akarbowy.hiit.view.activities.SubscriptionActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by akarbowy on 02.12.2015.
 */
@Singleton
public class Navigator {

    public static final int ADD_SUBSCRIPTION = 10;

    @Inject
    public void Navigator() {
    }

    public void navigateToLogIn(Context context) {
        startActivity(context, LogInActivity.getCallingIntent(context));
    }

    public void navigateToMain(Context context) {
        startActivity(context, MainActivity.getCallingIntent(context));
    }

    public void navigateToSingUp(Context context) {
        startActivity(context, SignUpActivity.getCallingIntent(context));
    }

    public void navigateToNotifications(Context context) {
        startActivity(context, NotificationsActivity.getCallingIntent(context));
    }

    public void navigateToSubscriptions(Context context) {
        startActivity(context, SubscriptionActivity.getCallingIntent(context));
    }

    public void navigateToOwnEvents(Context context) {
        startActivity(context, OwnEventsActivity.getCallingIntent(context));
    }

    public void navigateToHistory(Context context) {
        startActivity(context, HistoryActivity.getCallingIntent(context));
    }

    public void navigateToEmptyEventForm(Context context) {
        startActivity(context, EventFormActivity.getCallingIntent(context, ""));
    }

    public void navigateToEventForm(Context context, String eventId) {
        startActivity(context, EventFormActivity.getCallingIntent(context, eventId));
    }

    public void navigateToEventDetails(Context context, String eventId) {
        startActivity(context, EventDetailsActivity.getCallingIntent(context, eventId));
    }

    public void navigateToSearch(SubscriptionActivity context) {
        if (context != null) {
            Intent intentToLaunch = SearchActivity.getCallingIntent(context);
            context.startActivityForResult(intentToLaunch, ADD_SUBSCRIPTION);
        }
    }

    private void startActivity(Context context, Intent intent) {
        if (context != null)
            context.startActivity(intent);
    }
}
