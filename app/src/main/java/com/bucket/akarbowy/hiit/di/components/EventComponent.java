package com.bucket.akarbowy.hiit.di.components;

import com.bucket.akarbowy.hiit.di.PerActivity;
import com.bucket.akarbowy.hiit.di.modules.ActivityModule;
import com.bucket.akarbowy.hiit.di.modules.EventModule;
import com.bucket.akarbowy.hiit.view.fragments.EventDetailsFragment;
import com.bucket.akarbowy.hiit.view.fragments.EventFormFragment;

import dagger.Component;

/**
 * Created by akarbowy on 09.12.2015.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, EventModule.class})
public interface EventComponent extends ActivityComponent {
    void inject(EventFormFragment eventFormFragment);
    void inject(EventDetailsFragment eventDetailsFragment);
}
