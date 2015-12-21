package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
    @Bind(R.id.search_box)
    EditText mSearchBox;
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        setUpView();
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
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
        mSearchBox.setOnClickListener(new View.OnClickListener() {
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

    private SubscriptionsAdapter.OnCancelClickListener mOnCancelSubClickListener = new SubscriptionsAdapter.OnCancelClickListener() {
        @Override
        public void onUnsubscribeClicked(String techId) {
            if (mSubscriptionPresenterImpl != null && !techId.isEmpty()) {
                mSubscriptionPresenterImpl.onUnsubscribe(techId);
            }
        }
    };
}
