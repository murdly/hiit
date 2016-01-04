
package com.bucket.akarbowy.hiit.di.modules;

import com.bucket.akarbowy.hiit.di.PerActivity;
import com.bucket.akarbowy.hiit.domain.interactor.AddSubscription;
import com.bucket.akarbowy.hiit.domain.interactor.CancelSub;
import com.bucket.akarbowy.hiit.domain.interactor.FindTechnology;
import com.bucket.akarbowy.hiit.domain.interactor.GetEnrolledList;
import com.bucket.akarbowy.hiit.domain.interactor.GetOrganizedList;
import com.bucket.akarbowy.hiit.domain.interactor.GetOwnEventsList;
import com.bucket.akarbowy.hiit.domain.interactor.GetParticipatedList;
import com.bucket.akarbowy.hiit.domain.interactor.GetRssList;
import com.bucket.akarbowy.hiit.domain.interactor.GetSubsList;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.parse.ParseUser;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akarbowy on 09.12.2015.
 */
@Module
public class UserModule {

    private ParseUser user;

    public UserModule(ParseUser user) {
        this.user = user;
    }

    @Provides
    @PerActivity
    @Named("rssList")
    UseCase provideGetRssListUseCase(GetRssList getRssList) {
        return getRssList;
    }

    @Provides
    @PerActivity
    @Named("enrolledList")
    UseCase provideGetEnrolledListUseCase(Repository repository) {
        return new GetEnrolledList(user, repository);
    }

    @Provides
    @PerActivity
    @Named("historyParticipatedList")
    UseCase provideGetHistoryParticipatedListUseCase(Repository repository) {
        return new GetParticipatedList(user, repository);
    }
    @Provides
    @PerActivity
    @Named("historyOrganizedList")
    UseCase provideGetHistoryOrganizedListUseCase(Repository repository) {
        return new GetOrganizedList(user, repository);
    }

    @Provides
    @PerActivity
    @Named("ownEventsList")
    UseCase provideGetOwnEventsListUseCase(Repository repository) {
        return new GetOwnEventsList(user, repository);
    }

    @Provides
    @PerActivity
    @Named("subsList")
    UseCase provideGetSubsListUseCase(Repository repository) {
        return new GetSubsList(user, repository);
    }

    @Provides
    @PerActivity
    @Named("cancelSub")
    UseCase provideCancelSubUseCase(Repository repository) {
        return new CancelSub(user, repository);
    }

    @Provides
    @PerActivity
    @Named("techQueryList")
    UseCase provideFindTechnologyUseCase(Repository repository) {
        return new FindTechnology(user, repository);
    }

    @Provides
    @PerActivity
    @Named("addSubscription")
    UseCase provideAddSubscriptionUseCase(Repository repository) {
        return new AddSubscription(user, repository);
    }
}
