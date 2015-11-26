package com.bucket.akarbowy.hiit.base;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by akarbowy on 26.11.2015.
 */
public abstract class BaseFragment <T extends BaseView, P extends BasePresenter> extends Fragment implements BaseView {
 private P mPresenter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override @SuppressWarnings("unchecked")
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mPresenter != null)
            mPresenter = getPresenter();

        mPresenter.attachView(this);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mPresenter.detachView();
    }

    protected abstract P getPresenter();

    @LayoutRes
    protected abstract int getLayout();
}
