package com.bucket.akarbowy.hiit.view.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.adomain.Event;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.presenters.EventFormPresenterImpl;
import com.bucket.akarbowy.hiit.utils.DateTimePickerUtil;

import java.util.Calendar;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class EventFormFragment extends BaseFragment implements EventFormView {
    private static final String ARGUMENT_KEY_EVENT_ID = "hiit.ARGUMENT_EVENT_ID";

    private String mEventId;

    @Inject
    EventFormPresenterImpl mEventFormPresenter;
    private DateTimePickerUtil mDateTimePicker;
    private Calendar mMinDate = Calendar.getInstance();
    private ProgressDialog mWaitingDialog;

    @Bind(R.id.layout_title)
    TextInputLayout mLayoutTitle;
    @Bind(R.id.event_title)
    EditText mTitle;
    @Bind(R.id.event_technology)
    TextView mTechnology;
    @Bind(R.id.event_date)
    TextView mDate;
    @Bind(R.id.event_time)
    TextView mTime;
    @Bind(R.id.event_localization)
    EditText mLocalization;
    @Bind(R.id.event_description)
    EditText mDescription;

    public EventFormFragment() {
        super();
    }

    public static EventFormFragment newInstance(String eventId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_KEY_EVENT_ID, eventId);
        EventFormFragment fragment = new EventFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void initialize() {
        getComponent(EventComponent.class).inject(this);
        mEventFormPresenter.setView(this);
        mEventId = getArguments().getString(ARGUMENT_KEY_EVENT_ID);
        mEventFormPresenter.initialize(mEventId);
        mDateTimePicker = new DateTimePickerUtil(getActivity().getSupportFragmentManager()); //todo inject
        mDateTimePicker.setOnDateTimeSetListener(mOnDateTimeSetListener);
        mDateTimePicker.setMinDate(mMinDate);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_event_form;
    }

    @OnClick(R.id.event_date)
    void showDatePicker() {
        mDateTimePicker.showDatePicker();
    }

    @OnClick(R.id.event_time)
    void showTimePicker() {
        mDateTimePicker.showTimePicker();
    }

    private DateTimePickerUtil.OnDateTimeSet mOnDateTimeSetListener = new DateTimePickerUtil.OnDateTimeSet() {
        @Override
        public void onDateSet(String date) {
            mDate.setText(date);
        }

        @Override
        public void onTimeSet(String time) {
            mTime.setText(time);
        }
    };

    public void save() {
        mEventFormPresenter.save(parseEventModel());
    }

    private Event parseEventModel() {
        Event event = new Event();
        event.setAuthor();
        event.setTitle(mTitle.getText().toString().trim());
//        event.setTechnologyId("techId"); //todo tylko te co subskrybuj
        event.setDateTime(mDateTimePicker.getCalendar().getTimeInMillis());
        event.setLocalization(mLocalization.getText().toString());
        event.setDescription(mDescription.getText().toString());
        return event;
    }

    @DebugLog
    @Override
    public boolean isValid() {
        return mLayoutTitle.getEditText().length() <= mLayoutTitle.getCounterMaxLength()
                && !mLocalization.getText().toString().isEmpty()
                && !mDescription.getText().toString().isEmpty();
    }

    @Override
    public void showViewWaiting() {
        mWaitingDialog = new ProgressDialog(getActivity());
        mWaitingDialog.setMessage(getString(R.string.dialog_msg_on_saving));
        mWaitingDialog.show();
    }

    @Override
    public void hideViewWaiting() {
        mWaitingDialog.dismiss();
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void showError(String msg) {
        showToastMessage(msg);
    }

    @Override public Context getContext() {
        return getActivity().getApplicationContext();
    }
}
