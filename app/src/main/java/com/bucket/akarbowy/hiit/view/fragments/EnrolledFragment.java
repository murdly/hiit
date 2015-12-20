package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.EnrolledPresenterImpl;
import com.bucket.akarbowy.hiit.view.EventListListener;
import com.bucket.akarbowy.hiit.view.adapters.EnrolledEventsAdapter;
import com.bucket.akarbowy.hiit.view.adapters.SectionedRecyclerAdapter;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.EnrolledView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class EnrolledFragment extends TabFragment implements EnrolledView{

    @Inject
    EnrolledPresenterImpl mEnrolledPresenter;

    @Bind(R.id.enrolled_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.fragment_enrolled_refresh_layout)
    SwipeRefreshLayout mRefreshLayout;
    @Bind(R.id.empty_view)
    TextView mEmptyView;

    private EnrolledEventsAdapter mAdapter;
    private SectionedRecyclerAdapter mSectionedAdapter;
    private EventListListener mEventListListener;

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
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mAdapter = new EnrolledEventsAdapter(getActivity(), new ArrayList<EventModel>());
        mAdapter.setOnItemClickListener(mOnItemClickListener);
        mSectionedAdapter = new SectionedRecyclerAdapter(getActivity(),
                R.layout.recycler_event_section, R.id.section_text, mAdapter);
        mRecyclerView.setAdapter(mSectionedAdapter);
        mRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        mRefreshLayout.setColorSchemeColors(ContextCompat.getColor(getActivity(), R.color.colorAccent));
    }

    private void initialize() {
        getComponent(UserComponent.class).inject(this);
        mEnrolledPresenter.setView(this);
        loadEnrolledList();
    }

    private void loadEnrolledList() {
        mEnrolledPresenter.initialize();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_enrolled;
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
        mRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    public void hideViewRefreshing() {
        mRefreshLayout.setRefreshing(false);
    }

    @Override
    public void adaptEventsList(List<EventModel> eventModelsList) {
        if (eventModelsList != null) {
            mAdapter.setEventsList(eventModelsList);
            if (!eventModelsList.isEmpty())
                mSectionedAdapter.setSections(mAdapter.defineSections());
        }
    }

    @Override
    public void viewEvent(EventModel eventModel) {
        if(mEventListListener != null)
            mEventListListener.onEventClicked(eventModel);
    }

    private EnrolledEventsAdapter.OnItemClickListener mOnItemClickListener = new EnrolledEventsAdapter.OnItemClickListener() {
        @Override
        public void onEventItemClicked(EventModel eventModel) {
            if (mEnrolledPresenter != null && eventModel != null)
                mEnrolledPresenter.onEventClicked(eventModel);
        }
    };

    private SwipeRefreshLayout.OnRefreshListener mOnRefreshListener = new SwipeRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            loadEnrolledList();
        }
    };
}