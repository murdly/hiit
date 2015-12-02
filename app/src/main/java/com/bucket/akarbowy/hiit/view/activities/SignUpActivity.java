package com.bucket.akarbowy.hiit.view.activities;

import android.content.Intent;
import android.util.Log;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SignUpActivity extends BaseActivity{

    @Bind(R.id.already_have_account)
    TextView log;

    @Override
    protected int getLayout() {
        return R.layout.sign_up;
    }

    @OnClick(R.id.already_have_account)
    public void log(){
        Log.i("sua", "log");
        Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(i);
    }
}
