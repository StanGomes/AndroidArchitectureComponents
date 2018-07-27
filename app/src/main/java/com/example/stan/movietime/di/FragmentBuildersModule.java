package com.example.stan.movietime.di;

import com.example.stan.movietime.view.ui.MovieDetailFragment;
import com.example.stan.movietime.view.ui.NowPlayingFragment;
import com.example.stan.movietime.view.ui.NowPlayingListFragment;
import com.example.stan.movietime.view.ui.PopularFragment;
import com.example.stan.movietime.view.ui.PopularListFragment;
import com.example.stan.movietime.view.ui.TopFragment;
import com.example.stan.movietime.view.ui.TopListFragment;
import com.example.stan.movietime.view.ui.UpcomingFragment;
import com.example.stan.movietime.view.ui.UpcomingListFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract NowPlayingFragment contributeTestNowPlaying();

    @ContributesAndroidInjector
    abstract TopFragment contributeTestTop();

    @ContributesAndroidInjector
    abstract PopularFragment contributeTestPopular();

    @ContributesAndroidInjector
    abstract UpcomingFragment contributeTestUpcoming();

    @ContributesAndroidInjector
    abstract NowPlayingListFragment nowPlayingListFragment();

    @ContributesAndroidInjector
    abstract PopularListFragment popularListFragment();

    @ContributesAndroidInjector
    abstract UpcomingListFragment upcomingListFragment();

    @ContributesAndroidInjector
    abstract TopListFragment topListFragment();

    @ContributesAndroidInjector
    abstract MovieDetailFragment movieDetailFragment();
}
