package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.MovieRepository;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/************************
 *Author : Stanley Gomes *
 *Since : 03/06/2018        *
 ************************/
public class MovieDetailViewModel extends ViewModel {

    private static final String TAG = MovieDetailViewModel.class.getSimpleName();
    private final MovieRepository movieRepository;


    @Inject
    public MovieDetailViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }


    public LiveData<Resource<MovieDetailEntity>> getMovieDetails(int id) {
        return movieRepository.getMovieDetails(Constants.API_KEY, id);
    }

}
