package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;

import com.example.stan.movietime.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

        if (savedInstanceState == null) {
            initNowPlayingFragment();
            initUpcomingFragment();
            initPopularFragment();
            initTopFragment();
        }


        FloatingActionButton floatingActionButton = findViewById(R.id.discover_fab);
        floatingActionButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), DiscoverActivity.class);
            startActivity(intent);
        });

    }

    private void initTopFragment() {
        navigateTo(TopFragment.newInstance(), R.id.top_fragment_container);
    }

    private void initPopularFragment() {
        navigateTo(PopularFragment.newInstance(), R.id.pop_fragment_container);
    }

    private void initUpcomingFragment() {
        navigateTo(UpcomingFragment.newInstance(), R.id.up_fragment_container);
    }

    private void initNowPlayingFragment() {
        navigateTo(NowPlayingFragment.newInstance(), R.id.now_fragment_container);
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }


    public void navigateTo(Fragment fragment, int container) {
            FragmentTransaction transaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(container, fragment);
        transaction.commit();
    }

    public void showDetails(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.main_container, fragment);
        transaction.commit();
    }
}
