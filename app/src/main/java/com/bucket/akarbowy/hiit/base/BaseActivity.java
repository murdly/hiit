package com.bucket.akarbowy.hiit.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bucket.akarbowy.hiit.Navigator;
import com.bucket.akarbowy.hiit.di.ApplicationComponent;
import com.bucket.akarbowy.hiit.di.HiitApplication;

import javax.inject.Inject;

/**
 * Created by akarbowy on 26.11.2015.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    @Inject
    protected Navigator mNavigator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getLayout() != -1) //todo static field or use different approach than dispatchactivity
            setContentView(getLayout());
        this.getApplicationComponent().inject(this);
    }

    protected void addFragment(int containerId, Fragment fragment){
        FragmentTransaction fragmentTransaction = this.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(containerId, fragment);
        fragmentTransaction.commit();
    }

    @LayoutRes
    protected abstract int getLayout();

    protected ApplicationComponent getApplicationComponent() {
        return ((HiitApplication) getApplication()).getApplicationComponent();
    }
}
