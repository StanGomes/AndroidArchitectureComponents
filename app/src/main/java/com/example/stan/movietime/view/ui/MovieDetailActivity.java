package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.stan.movietime.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MovieDetailActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private static final String ID_KEY = "id";
    static final String BACKDROP_TRANSITION = "backdrop_transition";
    private static final String POSTER_TRANSITION = "poster_transition";
    private static final String TITLE_TRANSITION = "title_transition";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    static Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        intent = getIntent();
        init();


    }

    private void init() {
        int movieId = intent.getIntExtra(ID_KEY, 0);
        String titleTransition = getIntent().getStringExtra(TITLE_TRANSITION);
        if (intent.getStringExtra(BACKDROP_TRANSITION) == null) {
            String posterTransition = intent.getStringExtra(POSTER_TRANSITION);
            navigateTo(MovieDetailFragment.newInstance(movieId, titleTransition, posterTransition), R.id.detail_container, "movie_detail_fragment");
        } else {
            String backdropTransition = intent.getStringExtra(BACKDROP_TRANSITION);
            navigateTo(MovieDetailFragment.newInstance(movieId, titleTransition, backdropTransition), R.id.detail_container, "movie_detail_fragment");
        }



    }

    public void navigateTo(Fragment fragment, int container, String fragmentTag) {
        if (fragmentTag != null) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container, fragment, fragmentTag);
            transaction.commit();
        }

    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}

