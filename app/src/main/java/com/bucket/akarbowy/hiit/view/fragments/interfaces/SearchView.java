package com.bucket.akarbowy.hiit.view.fragments.interfaces;

import com.bucket.akarbowy.hiit.base.BaseView;
import com.bucket.akarbowy.hiit.model.TechnologyModel;

import java.util.List;

/**
 * Created by akarbowy on 02.12.2015.
 */
public interface SearchView extends BaseView {

    void showViewLoading();

    void hideViewLoading();

    void hideResultsContainer();

    void showResultsContainer();

    void setResultsList(List<TechnologyModel> resultsList);

    void finishSearch();
}
