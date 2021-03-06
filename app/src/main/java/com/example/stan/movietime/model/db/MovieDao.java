package com.example.stan.movietime.model.db;

import com.example.stan.movietime.model.db.entity.CreditsEntity;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

/************************
 *Author : Stanley Gomes *
 *Since : 30/05/2018        *
 ************************/

@Dao
public interface MovieDao {

    @Query("SELECT * FROM now_playing")
    LiveData<List<NowPlayingEntity>> getNowPlaying();

    @Query("SELECT * FROM upcoming")
    LiveData<List<UpcomingEntity>> getUpcoming();

    @Query("SELECT * FROM popular")
    LiveData<List<PopularEntity>> getPopular();

    @Query("SELECT * FROM top")
    LiveData<List<TopEntity>> getTop();

    @Query("SELECT * FROM movie_detail where id = :id")
    LiveData<MovieDetailEntity> getMovieDetail(int id);

    @Query("SELECT * FROM credits where id = :id")
    LiveData<CreditsEntity> getMovieCredits(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveNowPlaying(List<NowPlayingEntity> nowPlayingEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void savePopular(List<PopularEntity> popularEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveUpcoming(List<UpcomingEntity> upcomingEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveTop(List<TopEntity> topEntities);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovieDetails(MovieDetailEntity movieDetailEntity);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovieCredits(CreditsEntity creditsEntity);

    @Query("DELETE FROM now_playing")
    void deleteNowPlaying();

    @Query("DELETE FROM popular")
    void deletePopular();

    @Query("DELETE FROM upcoming")
    void deleteUpcoming();

    @Query("DELETE FROM top")
    void deleteTop();

    @Query("DELETE FROM movie_detail WHERE id = :id")
    void deleteMovieDetail(int id);

    @Query("DELETE FROM credits WHERE id = :id")
    void deleteCredits(int id);

}
