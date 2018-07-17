package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.repository.MovieDetailRepository;
import com.example.stan.movietime.utils.Constants;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/************************
 *Author : Stanley Gomes *
 *Since : 03/06/2018        *
 ************************/
public class MovieDetailViewModel extends ViewModel {

    private final MovieDetailRepository movieDetailRepository;

    @Inject
    public MovieDetailViewModel(MovieDetailRepository movieDetailRepository) {
        this.movieDetailRepository = movieDetailRepository;
    }


    public LiveData<Resource<MovieDetailEntity>> getMovieDetails(int id) {
        return movieDetailRepository.getMovieDetails(Constants.API_KEY, id);
    }

}
