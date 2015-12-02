
package com.bucket.akarbowy.hiit.di;

import android.content.Context;

import com.bucket.akarbowy.hiit.base.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by akarbowy on 02.12.2015.
 */
@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
  void inject(BaseActivity baseActivity);

  Context context();
}
