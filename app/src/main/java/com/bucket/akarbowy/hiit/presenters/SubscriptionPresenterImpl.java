package com.bucket.akarbowy.hiit.presenters;

import com.bucket.akarbowy.hiit.domain.Technology;
import com.bucket.akarbowy.hiit.domain.interactor.DefaultSubscriber;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.exception.ErrorMessageFactory;
import com.bucket.akarbowy.hiit.model.TechnologyDataMapper;
import com.bucket.akarbowy.hiit.model.TechnologyModel;
import com.bucket.akarbowy.hiit.view.fragments.interfaces.SubscriptionView;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created by akarbowy on 20.12.2015.
 */
public class SubscriptionPresenterImpl implements SubscriptionPresenter {

    private SubscriptionView mSubscriptionView;
    private UseCase mGetSubscriptionListUseCase, mCancelSubUseCase;
    private TechnologyDataMapper mTechnologyDataMapper;

    @Inject
    SubscriptionPresenterImpl(@Named("subsList") UseCase getSubscriptionListUseCase,
                              @Named("cancelSub") UseCase cancelSubUseCase,
                              TechnologyDataMapper technologyDataMapper) {
        this.mGetSubscriptionListUseCase = getSubscriptionListUseCase;
        this.mCancelSubUseCase = cancelSubUseCase;
        this.mTechnologyDataMapper = technologyDataMapper;
    }

    public void setView(SubscriptionView view) {
        mSubscriptionView = view;
    }

    public void initialize() {
        loadSubsList();
    }

    private void loadSubsList() {
        mSubscriptionView.hideViewEmpty();
        mSubscriptionView.showViewRefreshing();
        this.getSubsList();
    }

    @Override
    public void onUnsubscribe(String techId) {
        mSubscriptionView.showViewRefreshing();
        mCancelSubUseCase.execute(new CancelSubSubscriber(), techId);
    }

    private void showSubsInView(List<Technology> technologies) {
        List<TechnologyModel> subsTechnologiesList = mTechnologyDataMapper.transform(technologies);
        mSubscriptionView.setSubsList(subsTechnologiesList);
    }

    private void showErrorMessage(Exception error) {
        String errorMessage = ErrorMessageFactory.create(mSubscriptionView.getContext(), error);
        mSubscriptionView.showError(errorMessage);
    }

    public void getSubsList() {
        mGetSubscriptionListUseCase.execute(new SubsListSubscriber());
    }

    private final class SubsListSubscriber extends DefaultSubscriber<List<Technology>> {

        @Override
        public void onNext(List<Technology> technologies) {
            if (technologies.isEmpty()) mSubscriptionView.showViewEmpty();
            else showSubsInView(technologies);
        }

        @Override
        public void onError(Throwable e) {
            mSubscriptionView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mSubscriptionView.hideViewRefreshing();
        }
    }

    private final class CancelSubSubscriber extends DefaultSubscriber<Boolean> {

        @Override
        public void onNext(Boolean noSubs) {
            if (noSubs) mSubscriptionView.showViewEmpty();
        }

        @Override
        public void onError(Throwable e) {
            mSubscriptionView.hideViewRefreshing();
            showErrorMessage((Exception) e);
        }

        @Override
        public void onCompleted() {
            mSubscriptionView.hideViewRefreshing();
            mSubscriptionView.removeSub();
        }
    }

    @Override
    public void onDestroy() {
        mCancelSubUseCase.unsubscribe();
        mGetSubscriptionListUseCase.unsubscribe();
    }
}