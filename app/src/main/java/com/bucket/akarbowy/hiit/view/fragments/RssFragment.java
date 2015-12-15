package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.RssPresenterImpl;
import com.bucket.akarbowy.hiit.view.adapters.RssEventsAdapter;
import com.bucket.akarbowy.hiit.view.adapters.SectionedRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
/**
    assumption: fetch all events on every refresh
 */
public class RssFragment extends TabFragment implements RssView {

    @Inject
    RssPresenterImpl mRssPresenter;

    @Bind(R.id.rss_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.empty_view)
    TextView mEmptyView;
    @Bind(R.id.empty_view_no_subs)
    TextView mEmptyViewNoSubs;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private RssEventsAdapter mAdapter;
    private SectionedRecyclerAdapter mSectionedAdapter;

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

    public void setUpView(){
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        //mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        mAdapter = new RssEventsAdapter(getActivity(), new ArrayList<EventModel>());
        mSectionedAdapter = new SectionedRecyclerAdapter(getActivity(),
                R.layout.recycler_rss_event_section, R.id.section_text, mAdapter);
        mRecyclerView.setAdapter(mSectionedAdapter);
    }

    private void initialize() {
        getComponent(UserComponent.class).inject(this);
        mRssPresenter.setView(this);
        loadRssList();
    }

    private void loadRssList() {
        mRssPresenter.initialize();
    }

    @Override
    public int getLayout() {
        return R.layout.fragment_rss;
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
    public void showViewWaiting() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewWaiting() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showViewEmptyNoSubs() {
        mEmptyViewNoSubs.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewEmptyNoSubs() {
        mEmptyViewNoSubs.setVisibility(View.GONE);
    }

    @Override
    public void adaptEventsList(List<EventModel> eventModelsList) {
        if(eventModelsList != null) {
            mAdapter.setEventsList(eventModelsList);
            if(!eventModelsList.isEmpty())
                mSectionedAdapter.setSections(mAdapter.defineSections());
        }
    }
}