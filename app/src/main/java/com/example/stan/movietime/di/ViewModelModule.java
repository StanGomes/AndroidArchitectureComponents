package com.example.stan.movietime.di;

import com.example.stan.movietime.viewModel.DiscoverViewModel;
import com.example.stan.movietime.viewModel.MovieDetailViewModel;
import com.example.stan.movietime.viewModel.NowPlayingViewModel;
import com.example.stan.movietime.viewModel.PopularViewModel;
import com.example.stan.movietime.viewModel.TopViewModel;
import com.example.stan.movietime.viewModel.UpcomingViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NowPlayingViewModel.class)
    abstract ViewModel bindNowPlayingViewModel(NowPlayingViewModel nowPlayingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PopularViewModel.class)
    abstract ViewModel bindPopularViewModel(PopularViewModel popularViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(UpcomingViewModel.class)
    abstract ViewModel bindUpcomingViewModel(UpcomingViewModel upcomingViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(TopViewModel.class)
    abstract ViewModel bindTopViewModel(TopViewModel topViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(MovieDetailViewModel.class)
    abstract ViewModel bindMovieDetailViewModel(MovieDetailViewModel movieDetailViewModel);

    @Binds
    @IntoMap
    @ViewModelKey(DiscoverViewModel.class)
    abstract ViewModel bindSearchViewModel(DiscoverViewModel discoverViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);

}
