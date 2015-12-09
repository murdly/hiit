package com.bucket.akarbowy.hiit.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.bucket.akarbowy.hiit.Navigator;
import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.HiitApplication;
import com.bucket.akarbowy.hiit.di.components.ApplicationComponent;
import com.bucket.akarbowy.hiit.di.modules.ActivityModule;

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

    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }

    public void startActivityWithAnimation(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

    public void finishActivityWithAnimation() {
        finish();
        overridePendingTransition(R.anim.activity_finnish_open_anim, R.anim.activity_finnish_close_anim);
    }

    public void startActivityForResultWithAnimation(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.activity_in_anim, R.anim.activity_out_anim);
    }

}
