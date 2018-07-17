package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.model.SearchResponse;

import java.util.List;
import java.util.Objects;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*************************
 *Author : Stanley Gomes *
 *************************/
@Singleton
public class SearchRepository {

    private static final String TAG = SearchRepository.class.getSimpleName();
    final private ApiService mApiService;

    @Inject
    SearchRepository(ApiService apiService) {
        this.mApiService = apiService;
    }

    public LiveData<List<SearchEntity>> searchMovie(String apiKey, String query) {

        final MutableLiveData<List<SearchEntity>> searchResults = new MutableLiveData<>();
        mApiService.searchMovie(apiKey, query).enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(@NonNull Call<SearchResponse> call, @NonNull Response<SearchResponse> response) {
                List<SearchEntity> searchEntities = Objects.requireNonNull(response.body()).getResults();
                searchResults.setValue(searchEntities);
                Log.d(TAG, "Number of movies retrieved: " + searchEntities.size());
            }

            @Override
            public void onFailure(@NonNull Call<SearchResponse> call, @NonNull Throwable t) {
                //TODO Error handling
                Log.d(TAG, "Error: " + t.toString());
            }
        });
        return searchResults;
    }

}
