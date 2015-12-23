package com.bucket.akarbowy.hiit;

import android.content.Context;
import android.content.Intent;

import com.bucket.akarbowy.hiit.view.activities.EventDetailsActivity;
import com.bucket.akarbowy.hiit.view.activities.EventFormActivity;
import com.bucket.akarbowy.hiit.view.activities.LogInActivity;
import com.bucket.akarbowy.hiit.view.activities.MainActivity;
import com.bucket.akarbowy.hiit.view.activities.NotificationsActivity;
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
        if (context != null) {
            Intent intentToLaunch = LogInActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMain(Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
           context.startActivity(intentToLaunch);

        }
    }

    public void navigateToSingUp(Context context) {
        if (context != null) {
            Intent intentToLaunch = SignUpActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }


    public void navigateToNotifications(Context context) {
        if (context != null) {
            Intent intentToLaunch = NotificationsActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSubscriptions(Context context) {
        if (context != null) {
            Intent intentToLaunch = SubscriptionActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToMyEvents(Context context) {
        if (context != null) {
            Intent intentToLaunch = SignUpActivity.getCallingIntent(context);
//            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToHistory(Context context) {
        if (context != null) {
            Intent intentToLaunch = SignUpActivity.getCallingIntent(context);
//            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToEmptyEventForm(Context context) {
        if (context != null) {
            Intent intentToLaunch = EventFormActivity.getCallingIntent(context, "");
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToEventForm(Context context, String eventId) {
        if (context != null) {
            Intent intentToLaunch = EventFormActivity.getCallingIntent(context, eventId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToEventDetails(Context context, String eventId) {
        if (context != null) {
            Intent intentToLaunch = EventDetailsActivity.getCallingIntent(context, eventId);
            context.startActivity(intentToLaunch);
        }
    }

    public void navigateToSearch(SubscriptionActivity context) {
        if (context != null) {
            Intent intentToLaunch = SearchActivity.getCallingIntent(context);
            context.startActivityForResult(intentToLaunch, ADD_SUBSCRIPTION);
        }
    }
}
