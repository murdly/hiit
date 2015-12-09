package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.widget.EditText;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.parse.LogInCallback;
import com.parse.ParseUser;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class LogInFragment extends BaseFragment {


    @Bind(R.id.login_username_mail)
    EditText mUsernameMail;
    @Bind(R.id.login_password)
    EditText mPassword;

    private LogInCallback mLogInListener;
    public static Fragment newInstance() {
        return new LogInFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof LogInCallback) {
            this.mLogInListener = (LogInCallback) activity;
        }
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_log_in;
    }

    @OnClick(R.id.log_in)
    public void logIn(){
        ParseUser.logInInBackground(mUsernameMail.getText().toString(),mPassword.getText().toString(), mLogInListener);
    }
}
