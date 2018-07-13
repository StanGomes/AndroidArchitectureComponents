package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.network.model.GenreResults;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.viewModel.MovieDetailViewModel;
import com.google.android.material.card.MaterialCardView;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import dagger.android.AndroidInjection;

public class MovieDetailActivity extends AppCompatActivity implements Injectable {

    private static final String ID_KEY = "id";
    private static final String TAG = MovieDetailActivity.class.getSimpleName();
    @Inject
    ViewModelProvider.Factory viewModelFactory;
    private TextView titleTv, summaryTv, releaseDateTv, runtimeTv, homepageTv,
            avgScore, genreTv, taglineTv, productionTv, productionText,
            budgetTv, revenueTv, budgetText, revenueText;
    private ImageView backdrop;
    private MaterialCardView mainCard;
    private NestedScrollView nestedScrollView;
    private ProgressBar progressBar;
    private Intent intent;

    private static boolean isColorDark(int color) {
        double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return !(darkness < 0.5);
    }

    private static String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + "h " + minutes + "min";
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);
        intent = getIntent();

        init();

    }

    private void init() {

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
        titleTv = findViewById(R.id.titletv);
        backdrop = findViewById(R.id.backdrop);
        summaryTv = findViewById(R.id.summary);
        runtimeTv = findViewById(R.id.runtime);
        avgScore = findViewById(R.id.score);
        nestedScrollView = findViewById(R.id.scroll_view);
        genreTv = findViewById(R.id.genre);
        taglineTv = findViewById(R.id.tagline);
        mainCard = findViewById(R.id.header_card);
        progressBar = findViewById(R.id.progressBar);
        productionTv = findViewById(R.id.production);
        productionText = findViewById(R.id.production_text);
        budgetTv = findViewById(R.id.budget);
        revenueTv = findViewById(R.id.revenue);
        budgetText = findViewById(R.id.budget_text);
        revenueText = findViewById(R.id.revenue_text);
        releaseDateTv = findViewById(R.id.release_date);

        observeViewModel(movieDetailViewModel);

    }

    private void observeViewModel(MovieDetailViewModel movieDetailViewModel) {
        int id = intent.getIntExtra("id", 0);
        movieDetailViewModel.getMovieDetails(id).observe(this, movieDetailResponseResource -> {
            if (movieDetailResponseResource != null) {
                if (movieDetailResponseResource.data != null) {
                    updateViews(movieDetailResponseResource.data);
                }
            }

        });

    }

    private void updateViews(MovieDetailEntity movieDetailEntityResource) {
        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        titleTv.setText(movieDetailEntityResource.getTitle());
        summaryTv.setText(movieDetailEntityResource.getOverview());
        avgScore.setText(" " + movieDetailEntityResource.getVoteAverage());
        runtimeTv.setText(formatHoursAndMinutes(movieDetailEntityResource.getRuntime()));
        taglineTv.setText(movieDetailEntityResource.getTagline());
        releaseDateTv.setText(movieDetailEntityResource.getReleaseDate());
        budgetTv.setText(numberFormat.format(movieDetailEntityResource.getBudget()));
        revenueTv.setText(numberFormat.format(movieDetailEntityResource.getRevenue()));
        productionTv.setText(movieDetailEntityResource.getProductionCompanies().toString().replace("[", "")
                .replace("]", "")
                .replace(", ", "\n"));
        List<GenreResults> genreResults = movieDetailEntityResource.getmGenreResults();
        genreTv.setText(genreResults.toString().replace("[", "").replace("]", ""));
        final RequestOptions backdropRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this)
                .asBitmap()
                .load(Constants.BACKDROP_PATH + movieDetailEntityResource.getBackdropPath())
                .apply(backdropRequestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        backdrop.setImageBitmap(resource);
                        progressBar.setVisibility(View.GONE);
                        Palette.from(resource).generate(palette -> {
                            if (palette != null) {
                                applyPalette(palette);
                            }
                        });
                    }
                });
    }

    private void applyPalette(Palette palette) {
        View decor = getWindow().getDecorView();
        int oldSystemUiFlags = decor.getSystemUiVisibility();
        int newSystemUiFlags = oldSystemUiFlags;
        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
        if (dominantSwatch != null) {
            mainCard.setBackgroundColor(dominantSwatch.getRgb());
            nestedScrollView.setBackgroundColor(dominantSwatch.getRgb());
            titleTv.setTextColor(dominantSwatch.getBodyTextColor());
            summaryTv.setTextColor(dominantSwatch.getBodyTextColor());
            runtimeTv.setTextColor(dominantSwatch.getTitleTextColor());
            genreTv.setTextColor(dominantSwatch.getBodyTextColor());
            avgScore.setTextColor(dominantSwatch.getBodyTextColor());
            taglineTv.setTextColor(dominantSwatch.getTitleTextColor());
            productionTv.setTextColor(dominantSwatch.getBodyTextColor());
            productionText.setTextColor(dominantSwatch.getTitleTextColor());
            budgetTv.setTextColor(dominantSwatch.getBodyTextColor());
            budgetText.setTextColor(dominantSwatch.getTitleTextColor());
            revenueTv.setTextColor(dominantSwatch.getBodyTextColor());
            revenueText.setTextColor(dominantSwatch.getTitleTextColor());
            releaseDateTv.setTextColor(dominantSwatch.getBodyTextColor());
            getWindow().setStatusBarColor(dominantSwatch.getRgb());
            getWindow().setNavigationBarColor(dominantSwatch.getRgb());
            if (isColorDark(dominantSwatch.getRgb())) {
                decor.setSystemUiVisibility(0);
            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    newSystemUiFlags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    newSystemUiFlags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                }
            }
            if (newSystemUiFlags != oldSystemUiFlags) {
                decor.setSystemUiVisibility(newSystemUiFlags);
            }

        }
    }
}

