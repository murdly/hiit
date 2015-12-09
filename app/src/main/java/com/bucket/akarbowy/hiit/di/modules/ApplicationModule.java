
package com.bucket.akarbowy.hiit.di.modules;

import android.content.Context;

import com.bucket.akarbowy.hiit.Navigator;
import com.bucket.akarbowy.hiit.di.HiitApplication;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akarbowy on 02.12.2015.
 */
@Module
public class ApplicationModule {
  private final HiitApplication application;

  public ApplicationModule(HiitApplication application) {
    this.application = application;
  }

  @Provides @Singleton
  Context provideApplicationContext() {
    return this.application;
  }

  @Provides @Singleton
  Navigator provideNavigator() {
    return new Navigator();
  }
}
