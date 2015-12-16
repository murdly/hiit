/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bucket.akarbowy.hiit.di.modules;

import com.bucket.akarbowy.hiit.adomain.interactor.GetEventDetails;
import com.bucket.akarbowy.hiit.adomain.interactor.SaveEvent;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.adomain.repository.EventRepository;
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
    UseCase provideGetEventDetailsUseCase(EventRepository eventRepository) {
        return new GetEventDetails(eventId, eventRepository);
    }

    @Provides
    @PerActivity
    @Named("saveEvent")
    UseCase provideSaveEventUseCase(SaveEvent saveEvent) {
        return saveEvent;
    }
}