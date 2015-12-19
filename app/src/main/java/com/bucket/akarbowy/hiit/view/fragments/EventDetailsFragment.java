package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatDialog;
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
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class EventDetailsFragment extends BaseFragment implements EventDetailsView {
    private static final String ARGUMENT_KEY_EVENT_ID = "hiit.ARGUMENT_EVENT_ID";

    public interface OnEditMenuItemListener {
        void onStartEdit();
    }

    private String mEventId;
    private OnEditMenuItemListener mOnEditMenuItemListener;
    private AppCompatDialog mOrganizerInfo;

    @Inject
    EventDetailsPresenterImpl mEventDetailsPresenter;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.enroll_button)
    FloatingActionButton mEnrollButton;
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
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnEditMenuItemListener) {
            mOnEditMenuItemListener = (OnEditMenuItemListener) activity;
        }
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
        mToolbar.setOnMenuItemClickListener(mOnMenuItemClickListener);
    }

    @Override
    public void inflateMenu(int menuId) {
        mToolbar.inflateMenu(menuId);
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

    @DebugLog
    @OnClick(R.id.enroll_button)
    public void enroll() {
        mEventDetailsPresenter.enrollUser();
    }

    private Toolbar.OnMenuItemClickListener mOnMenuItemClickListener =
            new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.action_event_organizer:
                            showOrganizerInfo();
                            return true;
                        case R.id.action_event_disenroll:
                            disenrollFromEvent();
                            return true;
                        case R.id.action_event_edit:
                            mOnEditMenuItemListener.onStartEdit();
                            return true;
                        case R.id.action_event_cancel:
                            return true;
                    }
                    return true;
                }
            };

    private void showOrganizerInfo() {
        mOrganizerInfo.show();
    }

    private void disenrollFromEvent() {
        mEventDetailsPresenter.disenrollUser();
    }

    @Override
    public void renderEvent(EventModel eventModel) {
        if (eventModel != null) {
//            mIcon.setImageDrawable();
            mTitle.setText(eventModel.getTitle());
            mDate.setText(eventModel.getDateAsString());
            mLocalization.setText(eventModel.getLocalization());
            mDescription.setText(eventModel.getDescription());
        }
    }

    @Override
    public void setOrganizerInfo(String username, String email) {
        if(username != null && email != null) {
            mOrganizerInfo = new AppCompatDialog(getActivity());
            mOrganizerInfo.setContentView(R.layout.dialog_info_organizer);
            mOrganizerInfo.setTitle(getString(R.string.event_organizer));
            ((TextView)mOrganizerInfo.findViewById(R.id.event_organizer_username)).setText(username);
            ((TextView)mOrganizerInfo.findViewById(R.id.event_organizer_email)).setText(email);
        }
    }

    @Override
    public void setEnrollmentIndicatorsActive(boolean enrolled) {
        ColorStateList active = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorAccent));
        ColorStateList inactive = ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.black_54));
        mEnrollButton.setBackgroundTintList(enrolled ? active : inactive);
        mEnrollButton.setEnabled(!enrolled);
    }

    @Override
    public void setOrganizerMenuItemsEnabled(boolean enabled) {

    }

    @Override
    public void setParticipantMenuItemsEnabled(boolean enabled) {
        mToolbar.getMenu().findItem(R.id.action_event_disenroll).setEnabled(enabled);

    }
}