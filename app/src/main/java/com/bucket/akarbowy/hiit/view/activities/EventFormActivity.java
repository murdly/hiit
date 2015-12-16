package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerEventComponent;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.di.modules.EventModule;
import com.bucket.akarbowy.hiit.view.fragments.EventFormFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class EventFormActivity extends BaseActivity implements HasComponent<EventComponent> {

    private static final String INSTANCE_STATE_PARAM_EVENT_ID = "hiit.INSTANCE_STATE_PARAM_EVENT_ID";
    private static final String INTENT_STATE_PARAM_EVENT_ID = "hiit.INTENT_STATE_PARAM_EVENT_ID";

    public static Intent getCallingIntent(Context context, String userId) {
        Intent callingIntent = new Intent(context, EventFormActivity.class);
        callingIntent.putExtra(INTENT_STATE_PARAM_EVENT_ID, userId);
        return callingIntent;
    }

    private String mEventId;
    private EventComponent mEventComponent;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        setUpToolbar();
        initialize(savedInstanceState);
        initializeInjector();
    }

    private void initialize(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            mEventId = getIntent().getStringExtra(INTENT_STATE_PARAM_EVENT_ID);
            addFragment(R.id.container, EventFormFragment.newInstance(mEventId));
        } else {
            mEventId = savedInstanceState.getString(INSTANCE_STATE_PARAM_EVENT_ID);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_toolbar_container;
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
    public EventComponent getComponent() {
        return mEventComponent;
    }

    private void setUpToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_close);
        mToolbar.setNavigationOnClickListener(mOnCloseClickListener);
        mToolbar.inflateMenu(R.menu.menu_event_form);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_event_save) {
                    EventFormFragment form = (EventFormFragment) getSupportFragmentManager()
                                    .findFragmentById(R.id.container);
                    form.save();
                    return true;
                }
                return false;
            }
        });
    }

    private View.OnClickListener mOnCloseClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
//            checkIfEdited()
            finish();
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}
