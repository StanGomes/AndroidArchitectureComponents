package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.model.repository.SearchRepository;
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
    private final SearchRepository searchRepository;

    @Inject
    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public LiveData<List<SearchEntity>> searchMovie(String query) {
        return searchRepository.searchMovie(Constants.API_KEY, query);
    }

}
