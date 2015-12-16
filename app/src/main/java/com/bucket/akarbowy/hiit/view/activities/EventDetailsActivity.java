package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerEventComponent;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.di.modules.EventModule;
import com.bucket.akarbowy.hiit.view.fragments.EventDetailsFragment;

public class EventDetailsActivity extends BaseActivity implements HasComponent<EventComponent> {

    private static final String INSTANCE_STATE_PARAM_EVENT_ID = "hiit.INSTANCE_STATE_PARAM_EVENT_ID";
    private static final String INTENT_STATE_PARAM_EVENT_ID = "hiit.INTENT_STATE_PARAM_EVENT_ID";

    public static Intent getCallingIntent(Context context, String eventId) {
        Intent callingIntent = new Intent(context, EventDetailsActivity.class);
        callingIntent.putExtra(INTENT_STATE_PARAM_EVENT_ID, eventId);
        return callingIntent;
    }

    private String mEventId;
    private EventComponent mEventComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize(savedInstanceState);
        initializeInjector();
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mEventId = getIntent().getStringExtra(INTENT_STATE_PARAM_EVENT_ID);
            addFragment(R.id.container, EventDetailsFragment.newInstance(mEventId));
        } else {
            mEventId = savedInstanceState.getString(INSTANCE_STATE_PARAM_EVENT_ID);
        }
    }

    private void initializeInjector() {
        this.mEventComponent = DaggerEventComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .eventModule(new EventModule(mEventId))
                .build();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (outState != null) {
            outState.putString(INSTANCE_STATE_PARAM_EVENT_ID, mEventId);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_empty_container;
    }


    @Override
    public EventComponent getComponent() {
        return mEventComponent;
    }
}