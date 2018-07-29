package com.example.stan.movietime.viewModel;

import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.model.repository.DiscoverRepository;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import javax.inject.Inject;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

/*************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018     *
 *************************/
public class DiscoverViewModel extends ViewModel {
    private final DiscoverRepository discoverRepository;

    @Inject
    public DiscoverViewModel(DiscoverRepository discoverRepository) {
        this.discoverRepository = discoverRepository;
    }

    public LiveData<List<SearchEntity>> searchMovie(String query) {
        return discoverRepository.searchMovie(Constants.API_KEY, query);
    }

}
