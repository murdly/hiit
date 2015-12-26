package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.EventListListener;
import com.bucket.akarbowy.hiit.view.adapters.MyPagerAdapter;
import com.bucket.akarbowy.hiit.view.enums.HistoryTab;
import com.bucket.akarbowy.hiit.view.fragments.TabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class HistoryActivity extends BaseActivity implements HasComponent<UserComponent>, EventListListener {

    private UserComponent mUserComponent;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;

    public static Intent getCallingIntent(Context context) {
        return new Intent(context, HistoryActivity.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeInjector();
    }

    private void initialize() {
        ButterKnife.bind(this);
        initViewPagerAndTabs();
        setUpToolbar();
    }

    private void setUpToolbar() {
        mToolbar.setTitle(getString(R.string.history));
        mToolbar.setNavigationIcon(R.drawable.ic_back);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void initViewPagerAndTabs() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(TabFragment.createInstance(HistoryTab.PARTICIPATED),
                HistoryTab.getTitle(this, HistoryTab.PARTICIPATED.getPosition()));
        pagerAdapter.addFragment(TabFragment.createInstance(HistoryTab.ORGANIZED),
                HistoryTab.getTitle(this, HistoryTab.ORGANIZED.getPosition()));
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_history;
    }

    private void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(getUserModule())
                .build();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mNavigator.navigateToEventDetails(this, eventModel.getId());
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }

//    @Override
//    public Intent getSupportParentActivityIntent() {
//        final Bundle bundle = new Bundle();
//        final Intent intent = new Intent(this, MainActivity.class);
//
//        bundle.putInt(MainActivity.SWITCH_TAB, MainTab.ACCOUNT.getPosition());
//        intent.putExtras(bundle);
//
//        return intent;
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}