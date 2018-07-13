package com.example.stan.movietime.model;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.NowPlayingResponse;
import com.example.stan.movietime.model.network.model.PopularResponse;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.model.network.model.SearchResponse;
import com.example.stan.movietime.model.network.model.TopResponse;
import com.example.stan.movietime.model.network.model.UpcomingResponse;
import com.example.stan.movietime.utils.AppExecutors;
import com.example.stan.movietime.utils.RefreshSchedule;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/

@Singleton
public class MovieRepository {

    private static final String TAG = MovieRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;

    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(10, TimeUnit.MINUTES);
    ;


    @Inject
    MovieRepository(AppExecutors executor, MovieDao movieDao, ApiService apiService) {
        this.appExecutors = executor;
        this.movieDao = movieDao;
        this.mApiService = apiService;
    }

    public LiveData<Resource<List<NowPlayingEntity>>> getNowPlayingList(String apiKey, String region) {
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

    public LiveData<Resource<List<UpcomingEntity>>> getUpcomingList(String apiKey, String region) {
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

    public LiveData<Resource<List<PopularEntity>>> getPopularList(String apiKey, String region) {
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

    public LiveData<Resource<List<TopEntity>>> getTopList(String apiKey, String region) {
        return new NetworkBoundResource<List<TopEntity>, TopResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull TopResponse item) {
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

//    public LiveData<Resource<List<SearchEntity>>> searchMovie(String apiKey, String query){
//        return new NetworkBoundResource<List<SearchEntity>, SearchResponse>(appExecutors){
//
//            @Override
//            protected void saveCallResult(@NonNull SearchResponse item) {
//                movieDao.saveSearchResults(item.getResults());
//            }
//
//            @Override
//            protected boolean shouldFetch(@Nullable List<SearchEntity> data) {
//                return data == null || data.isEmpty() || listRefreshTimer.shouldFetch("search");
//            }
//
//            @NonNull
//            @Override
//            protected LiveData<List<SearchEntity>> loadFromDb() {
//                return movieDao.getSearchResults();
//            }
//
//            @NonNull
//            @Override
//            protected Call<SearchResponse> createCall() {
//                return mApiService.searchMovie(apiKey, query);
//            }
//        }.asLiveData();
//    }

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
