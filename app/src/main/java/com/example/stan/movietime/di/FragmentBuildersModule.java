package com.example.stan.movietime.di;

import com.example.stan.movietime.view.ui.NowplayingFragment;
import com.example.stan.movietime.view.ui.PopularFragment;
import com.example.stan.movietime.view.ui.SearchFragment;
import com.example.stan.movietime.view.ui.TopFragment;
import com.example.stan.movietime.view.ui.UpcomingFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract NowplayingFragment contributeNowPlayingFragment();

    @ContributesAndroidInjector
    abstract UpcomingFragment contributeUpcomingFragment();

    @ContributesAndroidInjector
    abstract PopularFragment contributePopularFragment();

    @ContributesAndroidInjector
    abstract TopFragment contributeTopFragment();

    @ContributesAndroidInjector
    abstract SearchFragment contributeSearchFragment();
}
