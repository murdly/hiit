package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.view.enums.Tab;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class TabFragment extends Fragment {
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

    public abstract int getLayout();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }
}