package com.example.stan.movietime.view.ui;



import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.bumptech.glide.request.transition.Transition;
import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.model.db.entity.MovieDetailEntity;
import com.example.stan.movietime.model.db.entity.RecommendedEntity;
import com.example.stan.movietime.model.network.model.Cast;
import com.example.stan.movietime.model.network.model.GenreResults;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.view.adapter.CastAdapter;
import com.example.stan.movietime.view.adapter.RecommendedAdapter;
import com.example.stan.movietime.viewModel.MovieDetailViewModel;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.transition.TransitionInflater;
import me.zhanghai.android.materialratingbar.MaterialRatingBar;


public class MovieDetailFragment extends Fragment implements MovieClickListener, Injectable {

    private static final String TAG = MovieDetailFragment.class.getSimpleName();
    private static final String EXTRA_MOVIE_ID = "movie_id";
    private static final String BACKDROP_TRANSITION = "backdrop_transition";
    private static final String POSTER_TRANSITION = "poster_transition";
    private static final String TITLE_TRANSITION = "title_transition";

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private int id;
    private String imageTransition, titleTransition;
    private TextView titleTv, scoreTv, summaryTv, runtimeTv, releaseDateTv, genreTv;
    private ImageView backdropIv, posterIv;
    private MaterialRatingBar ratingBar;
    private MaterialCardView backdropCard, posterCard;
    private RecyclerView castRecyclerView, recommendRecyclerView;


    private static String formatHoursAndMinutes(int totalMinutes) {
        String minutes = Integer.toString(totalMinutes % 60);
        minutes = minutes.length() == 1 ? "0" + minutes : minutes;
        return (totalMinutes / 60) + "h " + minutes + "min";
    }

    public static MovieDetailFragment newInstance(int movieId, String titleTransition, String imageTransition) {
        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_MOVIE_ID, movieId);
        args.putString(BACKDROP_TRANSITION, imageTransition);
        args.putString(POSTER_TRANSITION, imageTransition);
        args.putString(TITLE_TRANSITION, titleTransition);
        movieDetailFragment.setArguments(args);
        return movieDetailFragment;
    }

    private int getMovieId() {
        if (getArguments() != null) {
            return getArguments().getInt(EXTRA_MOVIE_ID, 0);
        } else return 0;
    }

    private String getBackdropTransitionName() {
        if (getArguments() != null) {
            return getArguments().getString(BACKDROP_TRANSITION);
        } else return null;
    }

    private String getTitleTransitionName() {
        if (getArguments() != null) {
            return getArguments().getString(TITLE_TRANSITION);
        } else return null;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getMovieId();
        imageTransition = getBackdropTransitionName();
        titleTransition = getTitleTransitionName();
        postponeEnterTransition();
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
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
        backdropCard = view.findViewById(R.id.header_card);
        posterCard = view.findViewById(R.id.poster_card);
        releaseDateTv = view.findViewById(R.id.date_tv);
        ratingBar = view.findViewById(R.id.rating_bar);
        castRecyclerView = view.findViewById(R.id.cast_rv);
        recommendRecyclerView = view.findViewById(R.id.recommended_rv);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (MovieDetailActivity.intent.getStringExtra(BACKDROP_TRANSITION) == null) {
            posterCard.setTransitionName(imageTransition);
        } else {
            backdropCard.setTransitionName(imageTransition);
        }
        titleTv.setTransitionName(titleTransition);
        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this, viewModelFactory).get(MovieDetailViewModel.class);
        observeViewModel(movieDetailViewModel);
    }

    private void observeViewModel(MovieDetailViewModel movieDetailViewModel) {
        getMovieDetails(movieDetailViewModel);
        getCredits(movieDetailViewModel);
        getRecommendation(movieDetailViewModel);
    }

    private void getRecommendation(MovieDetailViewModel movieDetailViewModel) {
        movieDetailViewModel.getRecommended(id).observe(this, recommendedEntityResource -> {
            if (recommendedEntityResource != null) {
                updateRecommended(recommendedEntityResource);
            }
        });
    }

    private void updateRecommended(List<RecommendedEntity> recommendedEntityResource) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        RecommendedAdapter recommendedAdapter = new RecommendedAdapter(this, getContext(), recommendedEntityResource);
        recommendRecyclerView.setLayoutManager(linearLayoutManager);
        recommendRecyclerView.setAdapter(recommendedAdapter);
    }

    private void getCredits(MovieDetailViewModel movieDetailViewModel) {
        movieDetailViewModel.getCredits(id).observe(this, creditsResponseResource -> {
            if (creditsResponseResource.data != null) {
                updateCast(creditsResponseResource.data.getCasts());
            }
        });
    }

    private void updateCast(List<Cast> casts) {
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        CastAdapter castAdapter = new CastAdapter(casts, getContext());
        castRecyclerView.setLayoutManager(linearLayoutManager);
        castRecyclerView.setAdapter(castAdapter);
    }

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
                .load(Constants.BACKDROP_PATH + data.getBackdropPath())
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        startPostponedEnterTransition();
                        return false;
                    }
                })
                .into(backdropIv);


        Glide.with(this)
                .asBitmap()
                .load(Constants.POSTER_PATH + data.getPosterPath())
                .apply(backdropRequestOptions)
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, Transition<? super Bitmap> transition) {
                        posterIv.setImageBitmap(resource);
                    }
                });


    }

    @Override
    public void onItemClickListener(int movieId, String title, TextView sharedTextView, MaterialCardView sharedImageView) {
        Log.d(TAG, "Clicked on: " + title);
        String backdropTransitionName = ViewCompat.getTransitionName(sharedImageView);
        String titleTransitionName = ViewCompat.getTransitionName(sharedTextView);

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        intent.putExtra("backdrop_transition", backdropTransitionName);
        intent.putExtra("title_transition", titleTransitionName);
//        Pair<View, String> titlePair = Pair.create(sharedTextView, titleTransitionName);
//        Pair<View, String> backdropPair = Pair.create(sharedImageView, backdropTransitionName);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), sharedImageView, ViewCompat.getTransitionName(sharedImageView));
        startActivity(intent, optionsCompat.toBundle());
    }
}
