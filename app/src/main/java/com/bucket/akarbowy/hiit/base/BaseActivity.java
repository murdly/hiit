package com.bucket.akarbowy.hiit.base;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by akarbowy on 26.11.2015.
 */
public abstract class BaseActivity<T extends BaseView, P extends BasePresenter> extends AppCompatActivity implements BaseView {
    protected P mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        if(mPresenter == null)
            mPresenter = getPresenter();

        mPresenter.attachView(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        mPresenter.detachView();
    }

    protected abstract P getPresenter();

    @LayoutRes
    protected abstract int getLayout();


//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (getSupportFragmentManager().getFragments() != null) {
//            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
//                if(fragment != null)
//                    fragment.onActivityResult(requestCode, resultCode, data);
//            }
//        }
//        super.onActivityResult(requestCode, resultCode, data);
//    }
}
