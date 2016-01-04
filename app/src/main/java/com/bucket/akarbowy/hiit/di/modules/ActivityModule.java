package com.bucket.akarbowy.hiit.di.modules;

import android.support.v7.app.AppCompatActivity;

import com.bucket.akarbowy.hiit.di.PerActivity;

import dagger.Module;
import dagger.Provides;

/**
 * Created by akarbowy on 09.12.2015.
 */
@Module
public class ActivityModule {
  private final AppCompatActivity activity;

  public ActivityModule(AppCompatActivity activity) {
    this.activity = activity;
  }

  @Provides @PerActivity
  AppCompatActivity activity() {
    return activity;
  }
}
