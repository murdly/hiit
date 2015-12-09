package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.view.fragments.LogInFragment;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class LogInActivity extends BaseActivity implements LogInCallback{

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, LogInActivity.class);

        return callingIntent;
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
    public void done(ParseUser user, ParseException e) {
        if(user == null){
            Toast.makeText(this, "Użytkownik nie istnieje.", Toast.LENGTH_SHORT).show();
        }else{
            mNavigator.navigateToMain(this);
        }
    }
}
