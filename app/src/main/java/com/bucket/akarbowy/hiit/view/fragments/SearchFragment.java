package com.bucket.akarbowy.hiit.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.bucket.akarbowy.hiit.di.components.UserComponent;
import com.bucket.akarbowy.hiit.model.TechnologyModel;
import com.bucket.akarbowy.hiit.presenters.SearchPresenterImpl;
import com.bucket.akarbowy.hiit.view.adapters.SearchAdapter;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.SearchView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class SearchFragment extends BaseFragment implements SearchView {

    @Inject
    SearchPresenterImpl mSearchPresenterImpl;

    @Bind(R.id.results)
    ListView mResultsContainer;
    @Bind(R.id.search_box)
    EditText mSearchBox;
    @Bind(R.id.progress_bar)
    ProgressBar mProgressBar;

    private SearchAdapter mAdapter;

    public static SearchFragment newInstance() {
        return new SearchFragment();
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
        mAdapter = new SearchAdapter(getActivity(), new ArrayList<TechnologyModel>());
        mResultsContainer.setOnItemClickListener(mOnItemClickListener);
        mSearchBox.addTextChangedListener(mQueryWatcher);
    }

    private void initialize() {
        getComponent(UserComponent.class).inject(this);
        mSearchPresenterImpl.setView(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_search;
    }

    @Override
    public void showError(String msg) {
        showToastMessage(msg);
    }

    @Override
    public void hideResultsContainer() {
        mResultsContainer.setVisibility(View.GONE);
    }

    @Override
    public void showResultsContainer() {
        mResultsContainer.setVisibility(View.VISIBLE);
    }

    @Override
    public void showViewLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideViewLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    private TextWatcher mQueryWatcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence query, int start, int before, int count) {
            mSearchPresenterImpl.onSearchQuery(query.toString());
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    public void setResultsList(List<TechnologyModel> resultsList) {
        if (resultsList != null)
            mAdapter.setResultsList(resultsList);
    }

    private ListView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    };
}
