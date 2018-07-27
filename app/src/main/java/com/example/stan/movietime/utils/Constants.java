package com.example.stan.movietime.utils;

import com.example.stan.movietime.BuildConfig;

/************************
 *Author : Stanley Gomes *
 *Since : 02/06/2018        *
 ************************/

public final class Constants {
    public static final String BASE_URL = "https://api.themoviedb.org/3/";
    public static final String API_KEY = BuildConfig.TMDB_API;
    public static final String POSTER_PATH = "https://image.tmdb.org/t/p/w342/";
    public static final String BACKDROP_PATH = "https://image.tmdb.org/t/p/original/";
    public static final int NOW_PLAYING_LIST = 1;
    public static final int UPCOMING_LIST = 2;
    public static final int POPULAR_LIST = 3;
    public static final int TOP_LIST = 4;
}
