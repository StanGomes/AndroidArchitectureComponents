package com.example.stan.movietime.model.repository;

import android.util.Log;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.entity.CreditsEntity;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.db.entity.RecommendedEntity;
import com.example.stan.movietime.model.network.ApiService;
import com.example.stan.movietime.model.network.NetworkBoundResource;
import com.example.stan.movietime.model.network.model.RecommendedResponse;
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
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*************************
 *Author : Stanley Gomes *
 *************************/

@Singleton
public class MovieDetailRepository {

    private static final String TAG = MovieDetailRepository.class.getSimpleName();
    final private ApiService mApiService;
    private final MovieDao movieDao;
    private final AppExecutors appExecutors;
    private RefreshSchedule<String> listRefreshTimer = new RefreshSchedule<>(60, TimeUnit.MINUTES);

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
                Log.d(TAG, "Deleting movie detail");
                movieDao.deleteMovieDetail(id);
                Log.d(TAG, "saving movie details to movie detail table");
                movieDao.saveMovieDetails(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable MovieDetailEntity data) {
                return data == null || listRefreshTimer.shouldFetch("details");
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

    public LiveData<Resource<CreditsEntity>> getMovieCredits(String apiKey, int id) {
        return new NetworkBoundResource<CreditsEntity, CreditsEntity>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull CreditsEntity item) {
                Log.d(TAG, "saving movie credits to credits table");
                movieDao.saveMovieCredits(item);
            }

            @Override
            protected boolean shouldFetch(@Nullable CreditsEntity data) {
                return data == null;
            }

            @NonNull
            @Override
            protected LiveData<CreditsEntity> loadFromDb() {
                Log.d(TAG, "Loading movie credits from credits table");
                return movieDao.getMovieCredits(id);
            }

            @NonNull
            @Override
            protected Call<CreditsEntity> createCall() {
                Log.d(TAG, "fetching movie credits from network");
                return mApiService.getCredits(id, apiKey);
            }
        }.asLiveData();
    }

    public LiveData<List<RecommendedEntity>> getRecommended(int id, String apiKey) {
        final MutableLiveData<List<RecommendedEntity>> recommendedResult = new MutableLiveData<>();
        mApiService.getRecommended(id, apiKey).enqueue(new Callback<RecommendedResponse>() {
            @Override
            public void onResponse(Call<RecommendedResponse> call, Response<RecommendedResponse> response) {
                if (response.body() != null) {
                    List<RecommendedEntity> recommendedEntities = response.body().getRecommendedResults();
                    recommendedResult.setValue(recommendedEntities);
                }
            }

            @Override
            public void onFailure(Call<RecommendedResponse> call, Throwable t) {
                Log.d(TAG, "Error: " + t.toString());
            }
        });
        return recommendedResult;
    }

}
