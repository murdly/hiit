package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.EventDetailsPresenterImpl;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventDetailsView;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class EventDetailsFragment extends BaseFragment implements EventDetailsView {
    private static final String ARGUMENT_KEY_EVENT_ID = "hiit.ARGUMENT_EVENT_ID";

    private String mEventId;

    @Inject
    EventDetailsPresenterImpl mEventDetailsPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.event_technology_icon)
    ImageView mIcon;
    @Bind(R.id.event_title)
    TextView mTitle;
    @Bind(R.id.event_date)
    TextView mDate;
    @Bind(R.id.event_localization)
    TextView mLocalization;
    @Bind(R.id.event_description)
    TextView mDescription;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    public EventDetailsFragment() {
        super();
    }

    public static EventDetailsFragment newInstance(String eventId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_KEY_EVENT_ID, eventId);
        EventDetailsFragment fragment = new EventDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpToolbar();
        this.initialize();
    }

    private void initialize() {
        getComponent(EventComponent.class).inject(this);
        mEventDetailsPresenter.setView(this);
        mEventId = getArguments().getString(ARGUMENT_KEY_EVENT_ID);
        mEventDetailsPresenter.initialize(mEventId);
    }

    private void setUpToolbar() {
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        mToolbar.inflateMenu(R.menu.menu_event_details);
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.action_event_organizer) {
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_event_details;
    }

    @Override
    public void showViewWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewWaiting() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String msg) {
        showToastMessage(msg);
    }

    @Override
    public void renderEvent(EventModel eventModel) {
        if(eventModel != null){
//            mIcon.setImageDrawable();
            mTitle.setText(eventModel.getTitle());
            mDate.setText(eventModel.getDateAsString());
            mLocalization.setText(eventModel.getLocalization());
            mDescription.setText(eventModel.getDescription());
        }
    }
}
