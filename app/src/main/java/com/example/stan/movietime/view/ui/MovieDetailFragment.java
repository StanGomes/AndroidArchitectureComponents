package com.example.stan.movietime.view.ui;


import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.RecyclerView;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


/**
 * A simple {@link Fragment} subclass.
 */
public class MovieDetailFragment extends androidx.fragment.app.Fragment implements Injectable {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private int id;
    private TextView titleTv, scoreTv, summaryTv, runtimeTv, releaseDateTv, genreTv;
    private ImageView backdropIv, posterIv;
    private MaterialRatingBar ratingBar;
    private RecyclerView castRecyclerView;
//    private CastAdapter castAdapter;


    private static String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + "h " + minutes + "min";
    }

    public static MovieDetailFragment newInstance(int movieId) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt("movieId", movieId);
        movieDetailFragment.setArguments(args);

        return movieDetailFragment;
    }

    private int getMovieId() {
        if (getArguments() != null) {
            return getArguments().getInt("movieId", 0);
        } else return 0;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getMovieId();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        titleTv = view.findViewById(R.id.title_tv);
        backdropIv = view.findViewById(R.id.backdrop_Iv);
        posterIv = view.findViewById(R.id.poster_detail_iView);
        summaryTv = view.findViewById(R.id.summary_tv);
        runtimeTv = view.findViewById(R.id.runtime_tv);
        scoreTv = view.findViewById(R.id.score_tv);
        genreTv = view.findViewById(R.id.genre_tv);
        releaseDateTv = view.findViewById(R.id.date_tv);
        ratingBar = view.findViewById(R.id.rating_bar);
        castRecyclerView = view.findViewById(R.id.cast_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
//        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
//        final SnapHelper snapHelper = new LinearSnapHelper();
//        snapHelper.attachToRecyclerView(castRecyclerView);
//        castAdapter = new CastAdapter(getContext());
//        castRecyclerView.setLayoutManager(linearLayoutManager);
//        castRecyclerView.setAdapter(castAdapter);
        observeViewModel(movieDetailViewModel);
    }


    private void observeViewModel(MovieDetailViewModel movieDetailViewModel) {
        getMovieDetails(movieDetailViewModel);
//        getCredits(movieDetailViewModel);
    }

//    private void getCredits(MovieDetailViewModel movieDetailViewModel) {
//        movieDetailViewModel.getCredits(id).observe(this, creditsResponseResource -> {
//            if (creditsResponseResource.data != null) {
//                updateCast(creditsResponseResource.data.getCasts());
//            }
//        });
//    }

//    private void updateCast(List<Cast> casts) {
//        castAdapter.setCasts(casts);
//    }

    private void getMovieDetails(MovieDetailViewModel movieDetailViewModel) {
        movieDetailViewModel.getMovieDetails(id).observe(this, movieDetailResponseResource -> {
            if (movieDetailResponseResource.data != null) {
                updateViews(movieDetailResponseResource.data);
            }
        });
    }

    private void updateViews(MovieDetailEntity data) {
//        final NumberFormat numberFormat = NumberFormat.getNumberInstance(Locale.CANADA);
        titleTv.setText(data.getTitle());
        summaryTv.setText(data.getOverview());
        runtimeTv.setText(formatHoursAndMinutes(data.getRuntime()));
        releaseDateTv.setText(data.getReleaseDate());
        scoreTv.setText(String.valueOf(data.getVoteAverage()));
        List<GenreResults> genreResults = data.getmGenreResults();
        genreTv.setText(genreResults.toString().replace("[", "").replace("]", ""));

        ratingBar.setStepSize(0.01f);
        ratingBar.setRating((float) ((float) data.getVoteAverage() * 0.5));

        final RequestOptions backdropRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(this)
                .asBitmap()
                .load(Constants.BACKDROP_PATH + data.getBackdropPath())
                .apply(backdropRequestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        backdropIv.setImageBitmap(resource);
                    }
                });

        Glide.with(this)
                .asBitmap()
                .load(Constants.POSTER_PATH + data.getPosterPath())
                .apply(backdropRequestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        posterIv.setImageBitmap(resource);
                        Palette.from(resource).generate(palette -> {
                            if (palette != null) {
                                applyPalette(palette);
                            }
                        });
                    }
                });

    }

    private void applyPalette(Palette palette) {
        Palette.Swatch dominantSwatch = palette.getDominantSwatch();
        if (dominantSwatch != null) {
//            nestedScrollView.setBackgroundColor(dominantSwatch.getRgb());
//            summaryCard.setBackgroundColor(dominantSwatch.getRgb());
//            summaryTv.setTextColor(dominantSwatch.getBodyTextColor());
//            scoreCard.setBackgroundColor(dominantSwatch.getRgb());
//            scoreTv.setTextColor(dominantSwatch.getBodyTextColor());
        }
    }
}
