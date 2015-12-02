package com.bucket.akarbowy.hiit.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.bucket.akarbowy.hiit.Navigator;
import com.bucket.akarbowy.hiit.di.ApplicationComponent;
import com.bucket.akarbowy.hiit.di.HiitApplication;

import javax.inject.Inject;

import butterknife.ButterKnife;

/**
 * Created by akarbowy on 26.11.2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Inject
    Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        this.getApplicationComponent().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @LayoutRes
    protected abstract int getLayout();

    protected ApplicationComponent getApplicationComponent() {
        return ((HiitApplication)getApplication()).getApplicationComponent();
    }
}
