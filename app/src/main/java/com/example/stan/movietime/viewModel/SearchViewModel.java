package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.MovieRepository;
import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/*************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018     *
 *************************/
public class SearchViewModel extends ViewModel {
    private final MovieRepository movieRepository;
    private LiveData<List<SearchEntity>> resourceLiveData;

    @Inject
    public SearchViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<SearchEntity>> searchMovie(String query) {
        return movieRepository.searchMovie(Constants.API_KEY, query);
    }

}
