package com.example.stan.movietime.model.network;

import com.example.stan.movietime.model.db.entity.CreditsEntity;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.model.DiscoverResponse;
import com.example.stan.movietime.model.network.model.NowPlayingResponse;
import com.example.stan.movietime.model.network.model.PopularResponse;
import com.example.stan.movietime.model.network.model.RecommendedResponse;
import com.example.stan.movietime.model.network.model.SearchResponse;
import com.example.stan.movietime.model.network.model.TopResponse;
import com.example.stan.movietime.model.network.model.UpcomingResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/
public interface ApiService {

    String BASE_URL = "https://api.themoviedb.org/3/";

    @GET("movie/now_playing")
    Call<NowPlayingResponse> getNowPlaying(@Query("api_key") String apiKey, @Query("region") String region);

    @GET("movie/upcoming")
    Call<UpcomingResponse> getUpcoming(@Query("api_key") String apiKey, @Query("region") String region);

    @GET("movie/popular")
    Call<PopularResponse> getPopular(@Query("api_key") String apiKey, @Query("region") String region);

    @GET("movie/top_rated")
    Call<TopResponse> getTopRated(@Query("api_key") String apiKey, @Query("region") String region);

    @GET("movie/{id}")
    Call<MovieDetailEntity> getMovieDetails(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("search/movie")
    Call<SearchResponse> searchMovie(@Query("api_key") String apiKey, @Query("query") String query);

    @GET("movie/{id}/credits")
    Call<CreditsEntity> getCredits(@Path("id") int id, @Query("api_key") String apiKey);

    @GET("discover/movie")
    Call<DiscoverResponse> discoverMovies(@Query("api_key") String apiKey, @Query("region") String region);

    @GET("movie/{id}/recommendations")
    Call<RecommendedResponse> getRecommended(@Path("id") int id, @Query("api_key") String apiKey);

}
