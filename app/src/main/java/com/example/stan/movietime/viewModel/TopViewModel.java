package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.repository.TopRepository;
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
    public TopViewModel(TopRepository topRepository) {
        mMovieListLiveData = topRepository.getMovieList(Constants.API_KEY, "CA|US");
    }

    public LiveData<Resource<List<TopEntity>>> getMovies() {
        return mMovieListLiveData;
    }
}
