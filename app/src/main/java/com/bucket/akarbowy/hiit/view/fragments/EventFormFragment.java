package com.bucket.akarbowy.hiit.view.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.EventFormPresenterImpl;
import com.bucket.akarbowy.hiit.utils.DateTimePickerUtil;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EventFormView;

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
    private ProgressDialog mSavingDialog;

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
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

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
        mDateTimePicker = new DateTimePickerUtil(getActivity().getSupportFragmentManager());
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
        mEventFormPresenter.save();
    }

    @Override
    public EventModel getEventModel() {
        EventModel event = new EventModel(mEventId);
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
    public void showViewSaving() {
        mSavingDialog = new ProgressDialog(getActivity());
        mSavingDialog.setMessage(getString(R.string.dialog_msg_on_saving));
        mSavingDialog.show();
    }

    @Override
    public void hideViewSaving() {
        mSavingDialog.dismiss();
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
    public void renderEvent(EventModel eventModel) {
        if (eventModel != null) {
//            mIcon.setImageDrawable();
            mTitle.setText(eventModel.getTitle());
            mDateTimePicker.getCalendar().setTimeInMillis(eventModel.getDateTimeInMillis());
            mDate.setText(eventModel.getDateAsString());
            mLocalization.setText(eventModel.getLocalization());
            mDescription.setText(eventModel.getDescription());
        }
    }

    @Override
    public void close() {
        getActivity().finish();
    }

    @Override
    public void showError(String msg) {
        showToastMessage(msg);
    }
}
