package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.repository.NowPlayingRepository;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/
public class NowPlayingViewModel extends ViewModel {

    private final LiveData<Resource<List<NowPlayingEntity>>> mMovieListLiveData;

    @Inject
    public NowPlayingViewModel(NowPlayingRepository nowPlayingRepository) {
        mMovieListLiveData = nowPlayingRepository.getMovieList(Constants.API_KEY, "us");
    }
    public LiveData<Resource<List<NowPlayingEntity>>> getMovies() {
        return mMovieListLiveData;
    }

}
