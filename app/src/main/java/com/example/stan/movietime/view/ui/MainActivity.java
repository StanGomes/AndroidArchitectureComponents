package com.example.stan.movietime.view.ui;

import android.os.Bundle;

import com.example.stan.movietime.R;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initNowPlayingFragment();
        initUpcomingFragment();
        initPopularFragment();
        initTopFragment();

    }

    private void initTopFragment() {
        navigateTo(TopFragment.newInstance(), R.id.top_fragment_container, "top_fragment");
    }

    private void initPopularFragment() {
        navigateTo(PopularFragment.newInstance(), R.id.pop_fragment_container, "pop_fragment");
    }

    private void initUpcomingFragment() {
        navigateTo(UpcomingFragment.newInstance(), R.id.up_fragment_container, "upcoming_fragment");
    }

    private void initNowPlayingFragment() {
        navigateTo(NowPlayingFragment.newInstance(), R.id.now_fragment_container, "top_fragment");
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    public void navigateTo(Fragment fragment, int container, String fragmentTag) {
        if (fragmentTag != null) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container, fragment, fragmentTag);
            transaction.commit();
        }

    }
}
