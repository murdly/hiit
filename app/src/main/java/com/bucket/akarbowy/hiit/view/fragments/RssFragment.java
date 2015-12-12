package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.EventModel;
import com.bucket.akarbowy.hiit.presenters.RssPresenterImpl;
import com.bucket.akarbowy.hiit.view.adapters.SimpleAdapter;
import com.bucket.akarbowy.hiit.view.adapters.SimpleSectionedRecyclerViewAdapter;

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

    private SimpleAdapter mAdapter;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;

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

        mAdapter = new SimpleAdapter(getActivity(), new ArrayList<EventModel>());
        mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(getActivity(),
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
    public void showViewWaiting() {

    }

    @Override
    public void hideViewWaiting() {

    }

    @Override
    public void adaptEventsList(List<EventModel> eventModelsList) {
        if(eventModelsList != null) {
            mAdapter.setEventsList(eventModelsList);
            mSectionedAdapter.setSections(mAdapter.defineSections());
        }
    }
}