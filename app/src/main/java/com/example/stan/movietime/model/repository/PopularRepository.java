package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.PopularResponse;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.AppExecutors;
import com.example.stan.movietime.utils.RefreshSchedule;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import retrofit2.Call;

/*************************
 *Author : Stanley Gomes *
 *************************/

@Singleton
public class PopularRepository implements Repository<PopularEntity> {

    private static final String TAG = PopularRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(10, TimeUnit.MINUTES);

    @Inject
    PopularRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    @Override
    public LiveData<Resource<List<PopularEntity>>> getMovieList(String apiKey, String region) {
        return new NetworkBoundResource<List<PopularEntity>, PopularResponse>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull PopularResponse item) {
                Log.d(TAG, "Saving item to popular table");
                movieDao.savePopular(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<PopularEntity> data) {
                return data == null || data.isEmpty() || listRefreshTimer.shouldFetch("pop");
            }

            @NonNull
            @Override
            protected LiveData<List<PopularEntity>> loadFromDb() {
                Log.d(TAG, "Loading item from popular table");
                return movieDao.getPopular();
            }

            @NonNull
            @Override
            protected Call<PopularResponse> createCall() {
                Log.d(TAG, "fetching popular from network");
                return mApiService.getPopular(apiKey, region);
            }
        }.asLiveData();
    }
}
