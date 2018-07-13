package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.MovieRepository;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/************************
 *Author : Stanley Gomes *
 *Since : 02/06/2018        *
 ************************/

public class UpcomingViewModel extends ViewModel {
    private LiveData<Resource<List<UpcomingEntity>>> mMovieListLiveData;

    @Inject
    public UpcomingViewModel(MovieRepository movieRepository) {
        mMovieListLiveData = movieRepository.getUpcomingList(Constants.API_KEY, "us");
    }

    public LiveData<Resource<List<UpcomingEntity>>> getMovies() {
        return mMovieListLiveData;
    }
}
