package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bucket.akarbowy.hiit.R;
import com.parse.ParseUser;

import butterknife.OnClick;

public class AccountFragment extends TabFragment {

    public interface OnAccountActionListener {
        void onLogOut();

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

    }

    @Override
    public int getLayout() {
        return R.layout.fragment_account;
    }

    @OnClick(R.id.logout)
    public void logout(){
        ParseUser.logOut();
        mAccountActionListener.onLogOut();
    }
}