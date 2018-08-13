package com.example.stan.movietime;

import android.app.Activity;
import android.app.Application;

import com.example.stan.movietime.di.AppInjector;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

/************************
 *Author : Stanley Gomes *
 *Since : 21/06/2018        *
 ************************/

public class MoviesApp extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

//    private RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            return;
//        }
//        LeakCanary.install(this);
        AppInjector.init(this);

    }

//    public static RefWatcher getRefWatcher(Context context) {
//        MoviesApp application = (MoviesApp) context.getApplicationContext();
//        return application.refWatcher;
//    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return dispatchingAndroidInjector;
    }
}
