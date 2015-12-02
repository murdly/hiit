package com.bucket.akarbowy.hiit.view.activities;

import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.presenters.SignUpPresenter;
import com.bucket.akarbowy.hiit.presenters.SignUpPresenterImpl;
import com.bucket.akarbowy.hiit.presenters.SignUpView;

import butterknife.Bind;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class LogInActivity extends BaseActivity<SignUpView, SignUpPresenter> implements BaseView {

    @Bind(R.id.log_in)
    TextView log;

    @Override
    protected SignUpPresenter getPresenter() {
        return new SignUpPresenterImpl();
    }

    @Override
    protected int getLayout() {
        return R.layout.login;
    }


}
