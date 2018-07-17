package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.repository.PopularRepository;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/************************
 *Author : Stanley Gomes *
 *Since : 02/06/2018        *
 ************************/

public class PopularViewModel extends ViewModel {
    private final LiveData<Resource<List<PopularEntity>>> mMovieListLiveData;

    @Inject
    public PopularViewModel(PopularRepository popularRepository) {
        mMovieListLiveData = popularRepository.getMovieList(Constants.API_KEY, "us");
    }

    public LiveData<Resource<List<PopularEntity>>> getMovies() {
        return mMovieListLiveData;
    }
}
