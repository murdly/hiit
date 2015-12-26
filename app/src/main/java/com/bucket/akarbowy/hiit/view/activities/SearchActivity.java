package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.view.enums.MainTab;
import com.bucket.akarbowy.hiit.view.fragments.SearchFragment;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SearchActivity extends BaseActivity implements HasComponent<UserComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SearchActivity.class);
    }

    private UserComponent mUserComponent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        initialize();
        initializeInjector();
    }

    private void initialize() {
        addFragment(R.id.container, SearchFragment.newInstance());
    }

    private void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(getUserModule())
                .build();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_empty_container;
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(MainActivity.SWITCH_TAB, MainTab.ACCOUNT.getPosition());
        intent.putExtras(bundle);

        return intent;
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }
}
