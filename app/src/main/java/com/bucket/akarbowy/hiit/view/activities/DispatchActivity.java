package com.bucket.akarbowy.hiit.view.activities;

import android.os.Bundle;

import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.parse.ParseUser;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class DispatchActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(ParseUser.getCurrentUser() != null)
            mNavigator.navigateToMain(this);
        else
            mNavigator.navigateToSingUp(this);
    }

    @Override
    protected int getLayout() {
        return -1;
    }
}
