package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.network.model.TopResponse;
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
public class TopRepository implements Repository<TopEntity> {

    private static final String TAG = TopRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(60, TimeUnit.MINUTES);

    @Inject
    TopRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    @Override
    public LiveData<Resource<List<TopEntity>>> getMovieList(String apiKey, String region) {
        return new NetworkBoundResource<List<TopEntity>, TopResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull TopResponse item) {
                Log.d(TAG, "Deleting top table");
                movieDao.deleteTop();
                Log.d(TAG, "Saving item to top_list table");
                movieDao.saveTop(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<TopEntity> data) {
                return data == null || data.isEmpty() || listRefreshTimer.shouldFetch("top");
            }

            @NonNull
            @Override
            protected LiveData<List<TopEntity>> loadFromDb() {
                Log.d(TAG, "Loading item from top_list table");
                return movieDao.getTop();
            }

            @NonNull
            @Override
            protected Call<TopResponse> createCall() {
                Log.d(TAG, "fetching top_list from network");
                return mApiService.getTopRated(apiKey, region);
            }
        }.asLiveData();
    }
}
