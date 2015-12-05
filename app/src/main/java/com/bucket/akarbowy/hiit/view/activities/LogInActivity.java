package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;

import butterknife.Bind;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class LogInActivity extends BaseActivity {

    @Bind(R.id.log_in)
    TextView log;

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, LogInActivity.class);

        return callingIntent;
    }
    @Override
    protected int getLayout() {
        return R.layout.fragment_log_in;
    }


}
