/**
 * Copyright (C) 2015 Fernando Cejas Open Source Project
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bucket.akarbowy.hiit.di.modules;

import com.bucket.akarbowy.hiit.adomain.interactor.GetRssList;
import com.bucket.akarbowy.hiit.adomain.interactor.UseCase;
import com.bucket.akarbowy.hiit.di.PerActivity;
import com.parse.ParseUser;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akarbowy on 09.12.2015.
 */
@Module
public class UserModule {

    private String userId;

    public UserModule(ParseUser user) {
        this.userId = user.getObjectId();
    }

    @Provides
    @PerActivity
    @Named("rssList")
    UseCase provideGetRssListUseCase(GetRssList getRssList) {
        return getRssList;
    }
}
