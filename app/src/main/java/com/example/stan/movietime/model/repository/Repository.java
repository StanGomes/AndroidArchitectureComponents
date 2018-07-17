package com.example.stan.movietime.model.repository;

import com.example.stan.movietime.model.network.model.Resource;

import java.util.List;

import androidx.lifecycle.LiveData;

/*************************
 *Author : Stanley Gomes *
 *************************/
public interface Repository<T> {

    LiveData<Resource<List<T>>> getMovieList(String apiKey, String region);

}
