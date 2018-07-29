package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.stan.movietime.R;
import com.example.stan.movietime.utils.Constants;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class ViewAllActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        intent = getIntent();
        init();
    }

    private void init() {

        if (intent.getIntExtra("now_playing_list", 0) == Constants.NOW_PLAYING_LIST) {
            navigateTo(NowPlayingListFragment.newInstance(), R.id.list_main_container, "now_playing_list_fragment");
        } else if (intent.getIntExtra("popular_list", 0) == Constants.POPULAR_LIST) {
            navigateTo(PopularListFragment.newInstance(), R.id.list_main_container, "popular_list_fragment");
        } else if (intent.getIntExtra("top_list", 0) == Constants.TOP_LIST) {
            navigateTo(TopListFragment.newInstance(), R.id.list_main_container, "top_list_fragment");
        } else if (intent.getIntExtra("upcoming_list", 0) == Constants.UPCOMING_LIST) {
            navigateTo(UpcomingListFragment.newInstance(), R.id.list_main_container, "upcoming_list_fragment");
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
