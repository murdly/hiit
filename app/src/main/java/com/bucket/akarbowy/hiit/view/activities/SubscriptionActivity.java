package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.bucket.akarbowy.hiit.Navigator;
import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.view.enums.Tab;
import com.bucket.akarbowy.hiit.view.fragments.SubscriptionFragment;

/**
 * Created by akarbowy on 02.12.2015.
 */
public class SubscriptionActivity extends BaseActivity implements SubscriptionFragment.OnSearchListener, HasComponent<UserComponent> {

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, SubscriptionActivity.class);
    }

    private UserComponent mUserComponent;
    private SubscriptionFragment mSubscriptionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeInjector();
    }

    private void initialize() {
        mSubscriptionFragment = SubscriptionFragment.newInstance(this);
        addFragment(R.id.container, mSubscriptionFragment);
    }

    private void initializeInjector() {
        this.mUserComponent = DaggerUserComponent.builder()
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
    public UserComponent getComponent() {
        return mUserComponent;
    }

    @Override
    public void onStartSearch() {
        mNavigator.navigateToSearch(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == Navigator.ADD_SUBSCRIPTION && resultCode == RESULT_OK)
            mSubscriptionFragment.onSubscriptionAdded(data);
    }

    @Override
    public Intent getSupportParentActivityIntent() {
        final Bundle bundle = new Bundle();
        final Intent intent = new Intent(this, MainActivity.class);

        bundle.putInt(MainActivity.SWITCH_TAB, Tab.ACCOUNT.getPosition());
        intent.putExtras(bundle);

        return intent;
    }
}
