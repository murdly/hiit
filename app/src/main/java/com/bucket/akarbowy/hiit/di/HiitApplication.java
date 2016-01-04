package com.bucket.akarbowy.hiit.di;

import android.app.Application;

import com.bucket.akarbowy.hiit.di.components.ApplicationComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerApplicationComponent;
import com.bucket.akarbowy.hiit.di.modules.ApplicationModule;
import com.bucket.akarbowy.hiit.domain.Event;
import com.bucket.akarbowy.hiit.domain.Technology;
import com.parse.Parse;
import com.parse.ParseObject;


/**
 * Created by akarbowy on 02.12.2015.
 */
public class HiitApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override public void onCreate() {
        super.onCreate();
        this.initializeInjector();

        this.initializeParseObjects();
        Parse.enableLocalDatastore(this);
        Parse.initialize(this);
    }

    private void initializeParseObjects() {
        ParseObject.registerSubclass(Event.class);
        ParseObject.registerSubclass(Technology.class);
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
