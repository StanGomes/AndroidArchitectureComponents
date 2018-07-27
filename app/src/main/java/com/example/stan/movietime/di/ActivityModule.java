package com.example.stan.movietime.di;

import com.example.stan.movietime.view.ui.MainActivity;
import com.example.stan.movietime.view.ui.MovieDetailActivity;
import com.example.stan.movietime.view.ui.ViewAllActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class ActivityModule {


    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MovieDetailActivity contributeDetailActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract MainActivity contributeTestActivity();

    @ContributesAndroidInjector(modules = FragmentBuildersModule.class)
    abstract ViewAllActivity allActivity();
}
