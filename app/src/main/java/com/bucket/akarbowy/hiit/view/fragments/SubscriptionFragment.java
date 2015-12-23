package com.bucket.akarbowy.hiit.view.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.TechnologyModel;
import com.bucket.akarbowy.hiit.presenters.SubscriptionPresenterImpl;
import com.bucket.akarbowy.hiit.view.adapters.SubscriptionsAdapter;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.SubscriptionView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class SubscriptionFragment extends BaseFragment implements SubscriptionView {

    public interface OnSearchListener {
        void onStartSearch();
    }

    @Inject
    SubscriptionPresenterImpl mSubscriptionPresenterImpl;

    @Bind(R.id.subs_list)
    RecyclerView mRecyclerView;
    @Bind(R.id.search_layout)
    AppBarLayout mSearchLayout;
    @Bind(R.id.empty_view)
    TextView mEmptyView;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private SubscriptionsAdapter mAdapter;
    private OnSearchListener mOnSearchListener;

    public static SubscriptionFragment newInstance(OnSearchListener onSearchListener) {
        SubscriptionFragment fragment =  new SubscriptionFragment();
        fragment.setOnSearchListener(onSearchListener);
        return fragment;
    }

    private void setOnSearchListener(OnSearchListener onSearchListener) {
        mOnSearchListener = onSearchListener;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        setUpView();
        initialize();
    }

    private void setUpView() {
        mAdapter = new SubscriptionsAdapter(getActivity(), new ArrayList<TechnologyModel>());
        mAdapter.setOnCancelClickListener(mOnCancelSubClickListener);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager
                .VERTICAL);
        layoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mAdapter);
        mSearchLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnSearchListener.onStartSearch();
            }
        });
    }

    private void initialize() {
        getComponent(UserComponent.class).inject(this);
        mSubscriptionPresenterImpl.setView(this);
        loadSubsList();
    }

    private void loadSubsList() {
        mSubscriptionPresenterImpl.initialize();
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_subscriptions;
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
        mEmptyView.setVisibility(View.GONE);
    }

    @Override
    public void showViewRefreshing() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewRefreshing() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void setSubsList(List<TechnologyModel> subsTechnologiesList) {
        if (subsTechnologiesList != null)
            mAdapter.setSubsList(subsTechnologiesList);
    }

    public void onSubscriptionAdded(Intent data) {
        TechnologyModel technology = data.getParcelableExtra(SearchFragment.PARCELABLE_TECHNOLOGY_ADDED);
        mAdapter.add(technology);
    }

    private SubscriptionsAdapter.OnCancelClickListener mOnCancelSubClickListener = new SubscriptionsAdapter.OnCancelClickListener() {
        @Override
        public void onUnsubscribeClicked(String techId) {
            if (mSubscriptionPresenterImpl != null && !techId.isEmpty()) {
                mSubscriptionPresenterImpl.onUnsubscribe(techId);
            }
        }
    };
}
