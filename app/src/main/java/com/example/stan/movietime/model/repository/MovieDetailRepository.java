package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.AppExecutors;

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
public class MovieDetailRepository {

    private static final String TAG = MovieDetailRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    @Inject
    MovieDetailRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    public LiveData<Resource<MovieDetailEntity>> getMovieDetails(String apiKey, int id) {
        return new NetworkBoundResource<MovieDetailEntity, MovieDetailEntity>(appExecutors) {
            @Override
            protected void saveCallResult(@NonNull MovieDetailEntity item) {
                Log.d(TAG, "saving movie details to movie detail table");
                movieDao.saveMovieDetails(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieDetailEntity data) {
                return data == null;
            }


            @NonNull
            @Override
            protected LiveData<MovieDetailEntity> loadFromDb() {
                Log.d(TAG, "Loading movie detail from movie detail table");
                return movieDao.getMovieDetail(id);
            }

            @NonNull
            @Override
            protected Call<MovieDetailEntity> createCall() {
                Log.d(TAG, "fetching movie detail from network");
                return mApiService.getMovieDetails(id, apiKey);
            }
        }.asLiveData();
    }

}
