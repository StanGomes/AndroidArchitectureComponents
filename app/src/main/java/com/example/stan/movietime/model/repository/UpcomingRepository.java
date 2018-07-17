package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.network.model.UpcomingResponse;
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
public class UpcomingRepository implements Repository<UpcomingEntity> {

    private static final String TAG = UpcomingRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(10, TimeUnit.MINUTES);

    @Inject
    UpcomingRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    @Override
    public LiveData<Resource<List<UpcomingEntity>>> getMovieList(String apiKey, String region) {
        return new NetworkBoundResource<List<UpcomingEntity>, UpcomingResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull UpcomingResponse item) {
                Log.d(TAG, "Saving item to upcoming table");
                movieDao.saveUpcoming(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<UpcomingEntity> data) {
                return data == null || data.isEmpty() || listRefreshTimer.shouldFetch("up");
            }

            @NonNull
            @Override
            protected LiveData<List<UpcomingEntity>> loadFromDb() {
                Log.d(TAG, "Loading item from upcoming table");
                return movieDao.getUpcoming();
            }

            @NonNull
            @Override
            protected Call<UpcomingResponse> createCall() {
                Log.d(TAG, "fetching upcoming from network");
                return mApiService.getUpcoming(apiKey, region);
            }
        }.asLiveData();
    }
}
