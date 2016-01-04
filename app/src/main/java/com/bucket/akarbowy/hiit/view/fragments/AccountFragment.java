package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.dialogs.YesNoDialog;
import com.parse.LogOutCallback;
import com.parse.ParseException;

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

        void onOwnEvents();

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
        mUsername.setText(com.parse.ParseUser.getCurrentUser().getUsername());
        mMail.setText(com.parse.ParseUser.getCurrentUser().getEmail());
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_account;
    }

    @OnClick(R.id.log_out)
    public void logout() {
        showYesNoDialog();
    }

    private void showYesNoDialog() {
        YesNoDialog.newInstance(getString(R.string.dialog_msg_logout),
                getString(R.string.dialog_logout), new YesNoDialog.OnActionListener() {
                    @Override
                    public void onPositiveButton() {
                        showProgressDialog();
                        logoutUser();
                    }
                }).show(getActivity().getSupportFragmentManager(), "logout");
    }

    private void showProgressDialog() {
        ProgressDialog dialog = new ProgressDialog(getActivity());
        dialog.setMessage(getString(R.string.dialog_msg_on_logout));
        dialog.show();
    }

    private void logoutUser() {
        ParseUser.logOutInBackground(new LogOutCallback() {
            @Override
            public void done(ParseException e) {
                if (e != null)
                    showToastMessage(getString(R.string.error_msg_default));
                else
                    mAccountActionListener.onLogOut();
            }
        });
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
    public void ownEvents() {
        mAccountActionListener.onOwnEvents();
    }

    @OnClick(R.id.menu_history)
    public void history() {
        mAccountActionListener.onHistory();
    }

}