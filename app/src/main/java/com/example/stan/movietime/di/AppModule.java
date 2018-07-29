package com.example.stan.movietime.di;


import android.app.Application;

import com.example.stan.movietime.model.db.MovieDao;
import com.example.stan.movietime.model.db.MovieDatabase;
import com.example.stan.movietime.model.network.ApiService;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module(includes = ViewModelModule.class)
class AppModule {
    @Singleton
    @Provides
    ApiService provideApiService() {
        return new Retrofit.Builder()
                .baseUrl(ApiService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }

    @Singleton
    @Provides
    MovieDatabase provideDatabase(Application application) {
        return Room.databaseBuilder(application, MovieDatabase.class, "movies_db")
                .fallbackToDestructiveMigration()
                .build();
    }


    @Singleton
    @Provides
    MovieDao provideMovieDao(MovieDatabase database) {
        return database.movieDao();
    }
}
