package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.adomain.Technology;
import com.bucket.akarbowy.hiit.adomain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.adomain.interactor.NoEmittingObserver;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.base.BasePresenter;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.TechnologyDataMapper;
import com.bucket.akarbowy.hiit.model.TechnologyModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.SearchView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class SearchPresenterImpl implements BasePresenter {

    private SearchView mSearchView;
    private UseCase mFindTechnologyUseCase, mAddSubscriptionUseCase;
    private TechnologyDataMapper mTechnologyDataMapper;

    @Inject
    SearchPresenterImpl(@Named("techQueryList") UseCase findTechnologyUseCase,
                        @Named("addSubscription") UseCase addSubscriptionUseCase,
                        TechnologyDataMapper technologyDataMapper) {
        this.mFindTechnologyUseCase = findTechnologyUseCase;
        this.mAddSubscriptionUseCase = addSubscriptionUseCase;
        this.mTechnologyDataMapper = technologyDataMapper;
    }

    public void setView(SearchView view) {
        mSearchView = view;
    }

    public void onSearchQuery(String query) {
        if (query.isEmpty()) {
            mSearchView.hideResultsContainer();
        } else {
            mSearchView.showViewLoading();
            getResultsList(query);
        }
    }

    public void onAddSubscription(String techId) {
        mSearchView.showViewLoading();
        mAddSubscriptionUseCase.execute(new AddSubscriptionSubscriber(), techId);
    }


    private void showResultsInView(List<Technology> technologies) {
        List<TechnologyModel> technologiesList = mTechnologyDataMapper.transform(technologies);
        mSearchView.setResultsList(technologiesList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mSearchView.getContext(), error);
        mSearchView.showError(errorMessage);
    }

    public void getResultsList(String query) {
        mFindTechnologyUseCase.execute(new TechnologyListSubscriber(), query);
    }

    private final class TechnologyListSubscriber extends DefaultSubscriber<List<Technology>> {
        @Override
        public void onNext(List<Technology> technologies) {
            showResultsInView(technologies);
        }

        @Override
        public void onError(Throwable e) {
            mSearchView.hideViewLoading();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mSearchView.hideViewLoading();
            mSearchView.showResultsContainer();
        }
    }

    private final class AddSubscriptionSubscriber extends NoEmittingObserver<Void> {

        @Override
        public void onCompleted() {
            mSearchView.hideViewLoading();
            mSearchView.finishSearch();
        }

        @Override
        public void onError(Throwable e) {
            mSearchView.hideViewLoading();
            showErrorMessage((Exception) e);
        }
    }


}