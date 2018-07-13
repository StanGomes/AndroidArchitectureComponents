package com.example.stan.movietime.view.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AccelerateDecelerateInterpolator;

import com.example.stan.movietime.R;
import com.example.stan.movietime.utils.NavigationHost;
import com.example.stan.movietime.utils.NavigationIconClickListener;
import com.google.android.material.button.MaterialButton;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;

public class MainActivity extends AppCompatActivity implements NavigationHost, HasSupportFragmentInjector {

    private final static String TAG = MainActivity.class.getSimpleName();
    @Inject
    DispatchingAndroidInjector<Fragment> fragmentDispatchingAndroidInjector;
    private MaterialSearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar bottomAppBar = findViewById(R.id.toolbar);
        bottomAppBar.setTitleTextColor(getResources().getColor(R.color.textColorPrimary));

        if (savedInstanceState == null) {
            navigateTo(NowplayingFragment.newInstance(), false);
            bottomAppBar.setTitle(R.string.now_playing);
        }
        MaterialButton nowPlayingButton = findViewById(R.id.now_button);
        MaterialButton upcomingButton = findViewById(R.id.upcoming_button);
        MaterialButton popularButton = findViewById(R.id.popular_button);
        MaterialButton topButton = findViewById(R.id.top_button);
        searchView = findViewById(R.id.search_view);

        initViews(bottomAppBar, searchView);


        nowPlayingButton.setOnClickListener(view -> {
            navigateTo(NowplayingFragment.newInstance(), true);
            bottomAppBar.setTitle(R.string.now_playing);
        });

        upcomingButton.setOnClickListener(view -> {
            navigateTo(UpcomingFragment.newInstance(), false);
            bottomAppBar.setTitle(R.string.upcoming);
        });

        popularButton.setOnClickListener(view -> {
            navigateTo(PopularFragment.newInstance(), false);
            bottomAppBar.setTitle(R.string.popular);
        });

        topButton.setOnClickListener(view -> {
            navigateTo(TopFragment.newInstance(), false);
            bottomAppBar.setTitle(R.string.top_rated);
        });

    }

    private void initViews(Toolbar toolbar, MaterialSearchView searchView) {
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new NavigationIconClickListener(this,
                findViewById(R.id.main_container),
                new AccelerateDecelerateInterpolator(),
                getResources().getDrawable(R.drawable.ic_nav_open_24dp),
                getResources().getDrawable(R.drawable.ic_nav_close_24dp)));
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                SearchFragment searchFragment = SearchFragment.newInstance(query);
                searchFragment.show(fragmentManager, "search_fragment");
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    @Override
    public void navigateTo(Fragment fragment, boolean addToBackstack) {
        FragmentTransaction transaction = getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_container, fragment);

        if (addToBackstack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        MenuItem menuItem = menu.findItem(R.id.search_action);
        searchView.setMenuItem(menuItem);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.search_action:
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (searchView.isSearchOpen()) {
            searchView.closeSearch();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentDispatchingAndroidInjector;
    }
}
