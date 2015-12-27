package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.view.fragments.SignUpFragment;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SignUpActivity extends BaseActivity implements SignUpFragment.OnRegistrationActionListener{


    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, SignUpActivity.class);
        callingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
    }

    private void initialize() {
        addFragment(R.id.container, SignUpFragment.newInstance());
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_sign_up;
    }

    @Override
    public void onLogIn() {
        mNavigator.navigateToLogIn(this);
    }

    @Override
    public void onRegister() {
        mNavigator.navigateToMain(this);
    }
}
