package com.bucket.akarbowy.hiit;

import android.content.Context;
import android.content.Intent;

import com.bucket.akarbowy.hiit.view.activities.MainActivity;

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

    public void navigateToMain(Context context) {
        if (context != null) {
            Intent intentToLaunch = MainActivity.getCallingIntent(context);
            context.startActivity(intentToLaunch);
        }
    }
}
