package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.NowPlayingResponse;
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
public class NowPlayingRepository implements Repository<NowPlayingEntity> {

    private static final String TAG = NowPlayingRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(10, TimeUnit.MINUTES);

    @Inject
    NowPlayingRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    @Override
    public LiveData<Resource<List<NowPlayingEntity>>> getMovieList(String apiKey, String region) {
        return new NetworkBoundResource<List<NowPlayingEntity>, NowPlayingResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull NowPlayingResponse item) {
                Log.d(TAG, "Saving item to now_playing table");
                movieDao.saveNowPlaying(item.getResults());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<NowPlayingEntity> data) {
                return data == null || data.isEmpty() || listRefreshTimer.shouldFetch("now");
            }

            @NonNull
            @Override
            protected Call<NowPlayingResponse> createCall() {
                Log.d(TAG, "fetching now_playing from network");
                return mApiService.getNowPlaying(apiKey, region);
            }

            @NonNull
            @Override
            protected LiveData<List<NowPlayingEntity>> loadFromDb() {
                Log.d(TAG, "loading item from now_playing table");
                return movieDao.getNowPlaying();
            }
        }.asLiveData();
    }
}
