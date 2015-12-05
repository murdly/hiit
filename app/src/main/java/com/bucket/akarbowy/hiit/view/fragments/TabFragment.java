package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.view.enums.Tab;

import butterknife.Bind;

public abstract class TabFragment extends BaseFragment {
    public final static String TAB_TYPE_KEY = "TabFragment$TabType";
    @Bind(R.id.emptyView)
    TextView mEmptyView;

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