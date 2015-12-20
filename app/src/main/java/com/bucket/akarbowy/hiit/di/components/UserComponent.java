package com.bucket.akarbowy.hiit.di.components;

import com.bucket.akarbowy.hiit.di.PerActivity;
import com.bucket.akarbowy.hiit.di.modules.ActivityModule;
import com.bucket.akarbowy.hiit.di.modules.UserModule;
import com.bucket.akarbowy.hiit.view.fragments.EnrolledFragment;
import com.bucket.akarbowy.hiit.view.fragments.RssFragment;
import com.bucket.akarbowy.hiit.view.fragments.SubscriptionFragment;

import dagger.Component;

/**
 * Created by akarbowy on 09.12.2015.
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = {ActivityModule.class, UserModule.class})
public interface UserComponent extends ActivityComponent {
    void inject(RssFragment rssFragment);
    void inject(EnrolledFragment enrolledFragment);
    void inject(SubscriptionFragment subscriptionFragment);
}
