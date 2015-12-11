package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.presenters.RssPresenterImpl;
import com.bucket.akarbowy.hiit.view.adapters.SimpleAdapter;
import com.bucket.akarbowy.hiit.view.adapters.SimpleSectionedRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

public class RssFragment extends TabFragment implements RssView {

    @Inject
    RssPresenterImpl mRssPresenter;

    @Bind(R.id.rss_list)
    RecyclerView mRecyclerView;

    private SimpleAdapter mAdapter;
    private SimpleSectionedRecyclerViewAdapter mSectionedAdapter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Your RecyclerView
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));

        String[] strings = new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n"
                , "o", "p", "q", "r", "s", "t", "u", "w", "x", "y", "z"};
        //Your RecyclerView.Adapter
        mAdapter = new SimpleAdapter(getActivity(), strings);


        //This is the code to provide a sectioned list
        List<SimpleSectionedRecyclerViewAdapter.Section> sections =
                new ArrayList<SimpleSectionedRecyclerViewAdapter.Section>();

        //Sections
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(0, "Section 1"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(5, "Section 2"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(12, "Section 3"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(14, "Section 4"));
        sections.add(new SimpleSectionedRecyclerViewAdapter.Section(22, "Section 5"));

        //Add your adapter to the sectionAdapter
        SimpleSectionedRecyclerViewAdapter.Section[] dummy =
                new SimpleSectionedRecyclerViewAdapter.Section[sections.size()];
        mSectionedAdapter = new SimpleSectionedRecyclerViewAdapter(getActivity(),
                R.layout.recycler_rss_event_section, R.id.section_text, mAdapter);
        mSectionedAdapter.setSections(sections.toArray(dummy));

        //Apply this adapter to the RecyclerView
        mRecyclerView.setAdapter(mSectionedAdapter);
        this.initialize();
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
    public void showViewWaiting() {

    }

    @Override
    public void hideViewWaiting() {

    }
}