package com.example.stan.movietime.di;


import android.app.Application;

import com.example.stan.movietime.MoviesApp;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

@Singleton
@Component(modules = {
        AndroidInjectionModule.class,
        AppModule.class,
        ActivityModule.class,
        ViewModelModule.class
})

public interface AppComponent {
    void inject(MoviesApp moviesApp);

    @Component.Builder
    interface Builder {
        @BindsInstance
        Builder application(Application application);

        AppComponent build();
    }

}
