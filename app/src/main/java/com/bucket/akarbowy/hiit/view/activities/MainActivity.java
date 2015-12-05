package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.view.adapters.MyPagerAdapter;
import com.bucket.akarbowy.hiit.view.enums.Tab;
import com.bucket.akarbowy.hiit.view.fragments.AccountFragment;
import com.bucket.akarbowy.hiit.view.fragments.TabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements AccountFragment.OnAccountActionListener {

    public static final String SWITCH_TAB = "hiit.actions.SWITCH_TAB";

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    private TabFragment mRssFragment, mParticipateFragment, mAccountFragment;

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, MainActivity.class);
        callingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initViewPagerAndTabs();
        setupTabsIcons();

        mToolbar.setTitle(Tab.getTitle(this, mTabLayout.getSelectedTabPosition()));
        setSupportActionBar(mToolbar);

        setProperTabIfRecurring();

    }

    private void setProperTabIfRecurring() {
        final Intent intent = getIntent();
        if (intent.hasExtra(SWITCH_TAB)) {
            final int tab = intent.getExtras().getInt(SWITCH_TAB);
            mViewPager.setCurrentItem(tab);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    private void initViewPagerAndTabs() {
        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
        mRssFragment = TabFragment.createInstance(Tab.RSS);
        mParticipateFragment = TabFragment.createInstance(Tab.PARTICIPATE);
        mAccountFragment = TabFragment.createInstance(Tab.ACCOUNT);

        pagerAdapter.addFragment(mRssFragment, null);
        pagerAdapter.addFragment(mParticipateFragment, null);
        pagerAdapter.addFragment(mAccountFragment, null);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupTabsIcons() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++)
            mTabLayout.getTabAt(i).setIcon(Tab.getIcon(this, i));
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            mToolbar.setTitle(Tab.getTitle(getApplicationContext(), tab.getPosition())); //todo get rid of context
            if (tab.getPosition() != Tab.RSS.getPosition())
                mFab.hide();
            else
                mFab.show();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @Override
    public void onLogOut() {
        finish();
    }

    @Override
    public void onNotifications() {
        Toast.makeText(this, "notifications", Toast.LENGTH_SHORT).show();
        mNavigator.navigateToNotifications(this);
    }

    @Override
    public void onSubscriptions() {
        Toast.makeText(this, "subscriptions", Toast.LENGTH_SHORT).show();
        mNavigator.navigateToSubscriptions(this);

    }

    @Override
    public void onMyEvents() {
        Toast.makeText(this, "myevents", Toast.LENGTH_SHORT).show();
        mNavigator.navigateToMyEvents(this);

    }

    @Override
    public void onHistory() {
        Toast.makeText(this, "history", Toast.LENGTH_SHORT).show();
        mNavigator.navigateToHistory(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }
}