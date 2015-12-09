package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.widget.EditText;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.EventComponent;
import com.bucket.akarbowy.hiit.presenters.EventFormPresenterImpl;
import com.bucket.akarbowy.hiit.view.custom.FormCommonFields;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class EventFormFragment extends BaseFragment implements EventFormView {
    private static final String ARGUMENT_KEY_EVENT_ID = "hiit.ARGUMENT_EVENT_ID";

    private String mEventId;

    @Inject
    EventFormPresenterImpl mEventFormPresenter;

    @Bind(R.id.event_title)
    EditText mTitle;
    @Bind(R.id.fields)
    FormCommonFields mFields;

    public EventFormFragment() { super(); }

    public static EventFormFragment newInstance(String eventId) {
        Bundle args = new Bundle();
        args.putString(ARGUMENT_KEY_EVENT_ID, eventId);
        EventFormFragment fragment = new EventFormFragment();
        fragment.setArguments(args);
        return fragment;
    }

//    @Override
//    public void onAttach(Activity activity) {
//        super.onAttach(activity);
//        if (activity instanceof LogInCallback) {
//            this.mLogInListener = (LogInCallback) activity;
//        }
//    }

    @Override public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void initialize() {
        getComponent(EventComponent.class).inject(this);
        mEventFormPresenter.setView(this);
        mEventId = getArguments().getString(ARGUMENT_KEY_EVENT_ID);
        mEventFormPresenter.initialize(mEventId);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_event_form;
    }

    public void save() {
        mEventFormPresenter.save();
    }
}
