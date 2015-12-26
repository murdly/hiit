package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.HistoryPresenterImpl;
import com.bucket.akarbowy.hiit.view.EventListListener;
import com.bucket.akarbowy.hiit.view.adapters.HistoryEventsAdapter;
import com.bucket.akarbowy.hiit.view.enums.HistoryTab;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.HistoryView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class HistoryFragment extends TabFragment implements HistoryView {

    @Inject
    HistoryPresenterImpl mHistoryPresenter;

    @Bind(R.id.results)
    ListView mList;
    @Bind(R.id.fragment_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.empty_view)
    TextView mEmptyView;

    private EventListListener mEventListListener;
    private HistoryEventsAdapter mAdapter;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof EventListListener) {
            this.mEventListListener = (EventListListener) activity;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.initialize();
    }

    private void setUpView() {
        mAdapter = new HistoryEventsAdapter(getActivity(), new ArrayList<EventModel>());
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(mOnItemClickListener);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
    }

    private void initialize() {
        getComponent(UserComponent.class).inject(this);
        mHistoryPresenter.setView(this);
        loadEventList();
    }

    private void loadEventList() {
        int position = getArguments().getInt(TAB_TYPE_KEY);
        if (HistoryTab.PARTICIPATED.getPosition() == position)
            mHistoryPresenter.loadParticipatedList();
        else if (HistoryTab.ORGANIZED.getPosition() == position)
            mHistoryPresenter.loadOrganizedList();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_history;
    }

    @Override
    public void showError(String msg) {
        showToastMessage(msg);
    }

    @Override
    public void hideViewEmpty() {
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showViewEmpty() {
        mEmptyView.setVisibility(View.VISIBLE);
    }

    @Override
    public void showViewRefreshing() {
        if (mRefreshLayout != null) {
            mRefreshLayout.post(new Runnable() {
                @Override
                public void run() {
                    mRefreshLayout.setRefreshing(true);
                }
            });
        }
    }

    @Override
    public void hideViewRefreshing() {
        if (mRefreshLayout != null)
            mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void setEventsList(List<EventModel> eventModelsList) {
        if (eventModelsList != null) {
            mAdapter.setEventsList(eventModelsList);
        }
    }

    @Override
    public void viewEvent(EventModel eventModel) {
        if (mEventListListener != null)
            mEventListListener.onEventClicked(eventModel);
    }

    private ListView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (mHistoryPresenter != null)
                mHistoryPresenter.onEventClicked(mAdapter.getItem(position));
        }
    };
    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadEventList();
        }
    };
}