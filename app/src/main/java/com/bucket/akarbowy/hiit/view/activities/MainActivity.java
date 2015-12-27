package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseActivity;
import com.bucket.akarbowy.hiit.di.HasComponent;
import com.bucket.akarbowy.hiit.di.components.DaggerUserComponent;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.view.EventListListener;
import com.bucket.akarbowy.hiit.view.adapters.MyPagerAdapter;
import com.bucket.akarbowy.hiit.view.enums.MainTab;
import com.bucket.akarbowy.hiit.view.fragments.AccountFragment;
import com.bucket.akarbowy.hiit.view.fragments.RssFragment;
import com.bucket.akarbowy.hiit.view.fragments.TabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

public class MainActivity extends BaseActivity implements HasComponent<UserComponent>,
        EventListListener, RssFragment.OnSubsPresentListener, AccountFragment.OnAccountActionListener {

    public static final String SWITCH_TAB = "hiit.actions.SWITCH_TAB";

    private UserComponent mUserComponent;

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, MainActivity.class);
        callingIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        return callingIntent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initialize();
        initializeInjector();
        setProperTabIfRecurring();
    }

    private void initialize() {
        ButterKnife.bind(this);
        initViewPagerAndTabs();
        setupTabsIcons();

        mToolbar.setTitle(MainTab.getTitle(this, mTabLayout.getSelectedTabPosition()));
        setSupportActionBar(mToolbar);
    }

    private void initializeInjector() {
        mUserComponent = DaggerUserComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .userModule(getUserModule())
                .build();
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
        pagerAdapter.addFragment(TabFragment.createInstance(MainTab.RSS), null);
        pagerAdapter.addFragment(TabFragment.createInstance(MainTab.PARTICIPATE), null);
        pagerAdapter.addFragment(TabFragment.createInstance(MainTab.ACCOUNT), null);
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(mOnTabSelectedListener);
    }

    @SuppressWarnings("ConstantConditions")
    private void setupTabsIcons() {
        for (int i = 0; i < mTabLayout.getTabCount(); i++)
            mTabLayout.getTabAt(i).setIcon(MainTab.getIcon(this, i));
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener() {

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            mToolbar.setTitle(MainTab.getTitle(getApplicationContext(), tab.getPosition())); //todo get rid of context
            if (tab.getPosition() != MainTab.RSS.getPosition()) mFab.hide();
            else mFab.show();
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    @DebugLog
    @Override
    public void onAddBtnEnabled(boolean enabled) {
        mFab.setEnabled(enabled);
    }

    @OnClick(R.id.fab)
    public void onAddEvent() {
        mNavigator.navigateToEmptyEventForm(this);
    }

    @Override
    public void onLogOut() {
        finish();
    }

    @Override
    public void onEventClicked(EventModel eventModel) {
        mNavigator.navigateToEventDetails(this, eventModel.getId());
    }

    @Override
    public void onNotifications() {
        mNavigator.navigateToNotifications(this);
    }

    @Override
    public void onSubscriptions() {
        mNavigator.navigateToSubscriptions(this);
    }

    @Override
    public void onOwnEvents() {
        mNavigator.navigateToOwnEvents(this);
    }

    @Override
    public void onHistory() {
        mNavigator.navigateToHistory(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
    }

    @Override
    public UserComponent getComponent() {
        return mUserComponent;
    }
}