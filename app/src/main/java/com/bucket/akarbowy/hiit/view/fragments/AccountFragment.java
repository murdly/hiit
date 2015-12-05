package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.OnClick;

public class AccountFragment extends TabFragment {

    @Bind(R.id.username)
    TextView mUsername;
    @Bind(R.id.mail)
    TextView mMail;

    public interface OnAccountActionListener {
        void onLogOut();

        void onNotifications();

        void onSubscriptions();

        void onMyEvents();

        void onHistory();
    }

    private OnAccountActionListener mAccountActionListener;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnAccountActionListener) {
            mAccountActionListener = (OnAccountActionListener) activity;
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mUsername.setText(ParseUser.getCurrentUser().getUsername());
        mMail.setText(ParseUser.getCurrentUser().getEmail());
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_account;
    }

    @OnClick(R.id.log_out)
    public void logout() {
        ParseUser.logOut();
        mAccountActionListener.onLogOut();
    }

    @OnClick(R.id.menu_notifications)
    public void notifications() {
        mAccountActionListener.onNotifications();
    }

    @OnClick(R.id.menu_subscriptions)
    public void subscriptions() {
        mAccountActionListener.onSubscriptions();

    }

    @OnClick(R.id.menu_my_events)
    public void myEvents() {
        mAccountActionListener.onMyEvents();
    }

    @OnClick(R.id.menu_history)
    public void history() {
        mAccountActionListener.onHistory();
    }

}