package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.view.enums.Tab;
import com.bucket.akarbowy.hiit.view.fragments.SubscriptionFragment;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SubscriptionActivity extends BaseActivity implements HasComponent<UserComponent> {

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, SubscriptionActivity.class);

        return callingIntent;
    }

    private UserComponent mUserComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeInjector();
    }

    private void initialize() {
        addFragment(R.id.container, SubscriptionFragment.newInstance());
    }

    private void initializeInjector() {
        this.mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(getUserModule())
                .build();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_empty_container;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(MainActivity.SWITCH_TAB, Tab.ACCOUNT.getPosition());
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }
}
