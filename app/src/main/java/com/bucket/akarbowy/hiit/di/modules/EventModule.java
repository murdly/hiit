
package com.bucket.akarbowy.hiit.di.modules;

import com.bucket.akarbowy.hiit.domain.interactor.CancelEvent;
import com.bucket.akarbowy.hiit.domain.interactor.DisenrollUser;
import com.bucket.akarbowy.hiit.domain.interactor.EnrollUser;
import com.bucket.akarbowy.hiit.domain.interactor.GetEventDetails;
import com.bucket.akarbowy.hiit.domain.interactor.SaveEvent;
import com.bucket.akarbowy.hiit.domain.interactor.UseCase;
import com.bucket.akarbowy.hiit.domain.repository.Repository;
import com.bucket.akarbowy.hiit.di.PerActivity;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akarbowy on 09.12.2015.
 */
@Module
public class EventModule {

    private String eventId;

    public EventModule() {
    }

    public EventModule(String eventId) {
        this.eventId = eventId;
    }

    @Provides
    @PerActivity
    @Named("eventDetails")
    UseCase provideGetEventDetailsUseCase(Repository repository) {
        return new GetEventDetails(eventId, repository);
    }

    @Provides
    @PerActivity
    @Named("createEvent")
    UseCase provideSaveEventUseCase(SaveEvent saveEvent) {
        return saveEvent;
    }

    @Provides
    @PerActivity
    @Named("cancelEvent")
    UseCase provideCancelEventUseCase(Repository repository) {
        return new CancelEvent(eventId, repository);
    }

    @Provides
    @PerActivity
    @Named("enrollUser")
    UseCase provideEnrollUserUseCase(Repository repository) {
        return new EnrollUser(eventId, repository);
    }

    @Provides
    @PerActivity
    @Named("disenrollUser")
    UseCase provideDisenrollUserUseCase(Repository repository) {
        return new DisenrollUser(eventId, repository);
    }
}