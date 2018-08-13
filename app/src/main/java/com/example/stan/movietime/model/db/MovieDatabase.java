package com.example.stan.movietime.model.db;

import com.example.stan.movietime.model.db.entity.CreditsEntity;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.db.entity.PopularEntity;
import com.example.stan.movietime.model.db.entity.RecommendedEntity;
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

/************************
 *Author : Stanley Gomes *
 *Since : 30/05/2018        *
 ************************/

@Database(entities = {MovieDetailEntity.class, NowPlayingEntity.class, PopularEntity.class, TopEntity.class, UpcomingEntity.class, CreditsEntity.class, RecommendedEntity.class}, version = 14, exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract MovieDao movieDao();

}
