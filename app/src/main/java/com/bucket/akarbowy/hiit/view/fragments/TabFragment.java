package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;

import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.view.enums.Tab;

public abstract class TabFragment extends BaseFragment {
    public final static String TAB_TYPE_KEY = "TabFragment$TabType";

    public static TabFragment createInstance(Tab tabType) {
        TabFragment tabFragment;

        if (tabType == Tab.RSS)
            tabFragment = new RssFragment();
        else if(tabType == Tab.PARTICIPATE)
            tabFragment = new ParticipateFragment();
        else
            tabFragment = new AccountFragment();

        Bundle bundle = new Bundle();
        bundle.putInt(TAB_TYPE_KEY, tabType.getPosition());
        tabFragment.setArguments(bundle);
        return tabFragment;
    }
}