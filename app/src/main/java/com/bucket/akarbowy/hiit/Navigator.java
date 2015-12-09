package com.bucket.akarbowy.hiit;

import android.content.Context;
import android.content.Intent;

import com.bucket.akarbowy.hiit.view.activities.LogInActivity;
import com.bucket.akarbowy.hiit.view.activities.MainActivity;
import com.bucket.akarbowy.hiit.view.activities.NotificationsActivity;
import com.bucket.akarbowy.hiit.view.activities.SignUpActivity;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by akarbowy on 02.12.2015.
 */
@Singleton
public class Navigator {

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
            Intent intentToLaunch = SignUpActivity.getCallingIntent(context);
//            context.startActivity(intentToLaunch);
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

}
