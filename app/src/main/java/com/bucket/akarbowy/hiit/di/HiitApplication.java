package com.bucket.akarbowy.hiit.di;

import android.app.Application;

import com.parse.Parse;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class HiitApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();

        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }

    private void initializeInjector() {
        this.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this))
                .build();
    }

    public ApplicationComponent getApplicationComponent() {
        return this.applicationComponent;
    }
}
