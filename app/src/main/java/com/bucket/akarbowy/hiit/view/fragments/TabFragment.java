package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;

import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.view.enums.HistoryTab;
import com.bucket.akarbowy.hiit.view.enums.MainTab;

public abstract class TabFragment extends BaseFragment {
    public final static String TAB_TYPE_KEY = "hiit.TabFragment$TabType";

    public static TabFragment createInstance(MainTab tabType) {
        TabFragment tabFragment;

        if (tabType == MainTab.RSS)
            tabFragment = new RssFragment();
        else if(tabType == MainTab.PARTICIPATE)
            tabFragment = new EnrolledFragment();
        else
            tabFragment = new AccountFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TAB_TYPE_KEY, tabType.getPosition());
        tabFragment.setArguments(bundle);
        return tabFragment;
    }

    public static TabFragment createInstance(HistoryTab tabType) {
        TabFragment tabFragment;

        if (tabType == HistoryTab.PARTICIPATED)
            tabFragment = new HistoryFragment();
        else
            tabFragment = new HistoryFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TAB_TYPE_KEY, tabType.getPosition());
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}