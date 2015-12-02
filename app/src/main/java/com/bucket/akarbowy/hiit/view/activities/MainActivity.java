package com.bucket.akarbowy.hiit.view.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.view.adapters.MyPagerAdapter;
import com.bucket.akarbowy.hiit.view.enums.Tab;
import com.bucket.akarbowy.hiit.view.fragments.TabFragment;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.toolbar)
    Toolbar mToolbar;
    @Bind(R.id.viewPager)
    ViewPager mViewPager;
    @Bind(R.id.tabLayout)
    TabLayout mTabLayout;
    @Bind(R.id.fab)
    FloatingActionButton mFab;

    private TabFragment mRssFragment, mParticipateFragment, mAccountFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initViewPagerAndTabs();
        setupTabsIcons();

        mToolbar.setTitle(Tab.getTitle(this, mTabLayout.getSelectedTabPosition()));
        setSupportActionBar(mToolbar);
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
        for(int i = 0; i< mTabLayout.getTabCount(); i++)
            mTabLayout.getTabAt(i).setIcon(Tab.getIcon(this, i));
    }

    private TabLayout.OnTabSelectedListener mOnTabSelectedListener = new TabLayout.OnTabSelectedListener(){

        @Override
        public void onTabSelected(TabLayout.Tab tab) {
            mViewPager.setCurrentItem(tab.getPosition());
            mToolbar.setTitle(Tab.getTitle(getApplicationContext(), tab.getPosition())); //todo get rid of context
        }

        @Override
        public void onTabUnselected(TabLayout.Tab tab) {

        }

        @Override
        public void onTabReselected(TabLayout.Tab tab) {

        }
    };

    public static Intent getCallingIntent(Context context) {
        Intent callingIntent = new Intent(context, MainActivity.class);

        return callingIntent;
    }
}