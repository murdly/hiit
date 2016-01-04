package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.TechnologyModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface SubscriptionView extends BaseView {

    void showViewRefreshing();

    void hideViewRefreshing();

    void hideViewEmpty();

    void showViewEmpty();

    void setSubsList(List<TechnologyModel> subsTechnologiesList);

    void removeSub();
}
