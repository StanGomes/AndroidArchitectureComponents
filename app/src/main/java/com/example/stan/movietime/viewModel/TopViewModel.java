package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.MovieRepository;
import com.example.stan.movietime.model.db.entity.TopEntity;
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

public class TopViewModel extends ViewModel {
    private LiveData<Resource<List<TopEntity>>> mMovieListLiveData;

    @Inject
    public TopViewModel(MovieRepository movieRepository) {
        mMovieListLiveData = movieRepository.getTopList(Constants.API_KEY, "us");
    }

    public LiveData<Resource<List<TopEntity>>> getMovies() {
        return mMovieListLiveData;
    }
}
