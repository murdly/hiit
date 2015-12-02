package com.bucket.akarbowy.hiit.view.activities;

import android.content.Intent;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.presenters.SignUpPresenter;
import com.bucket.akarbowy.hiit.presenters.SignUpPresenterImpl;
import com.bucket.akarbowy.hiit.presenters.SignUpView;

import butterknife.Bind;
import butterknife.OnClick;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SignUpActivity extends BaseActivity<SignUpView, SignUpPresenter> implements BaseView {

    @Bind(R.id.already_have_account)
    TextView log;

    @Override
    protected SignUpPresenter getPresenter() {
        return new SignUpPresenterImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.sign_up;
    }

    @OnClick(R.id.already_have_account)
    public void log(){
        Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
        startActivity(i);
    }
}
