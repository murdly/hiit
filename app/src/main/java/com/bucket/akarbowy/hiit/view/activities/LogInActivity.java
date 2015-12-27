package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.view.fragments.LogInFragment;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class LogInActivity extends BaseActivity implements LogInFragment.OnSuccessfulLoginCallBack {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, LogInActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        addFragment(R.id.container, LogInFragment.newInstance());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_log_in;
    }


    @Override
    public void onLogIn() {
        mNavigator.navigateToMain(this);

    }
}
