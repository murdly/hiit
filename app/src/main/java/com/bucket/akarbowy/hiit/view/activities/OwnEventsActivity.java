package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.EventListListener;
import com.bucket.akarbowy.hiit.view.enums.MainTab;
import com.bucket.akarbowy.hiit.view.fragments.OwnEventsFragment;

import butterknife.ButterKnife;

public class OwnEventsActivity extends BaseActivity implements HasComponent<UserComponent>, EventListListener {

    private UserComponent mUserComponent;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, OwnEventsActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeInjector();
    }

    private void initialize() {
        ButterKnife.bind(this);
        addFragment(R.id.container, OwnEventsFragment.newInstance(this));
    }

    private void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
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
    public UserComponent getComponent() {
        return mUserComponent;
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mNavigator.navigateToEventDetails(this, eventModel.getId());
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(MainActivity.SWITCH_TAB, MainTab.ACCOUNT.getPosition());
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}