package com.example.stan.movietime.view.ui;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.view.adapter.NowPlayingAdapter;
import com.example.stan.movietime.viewModel.NowPlayingViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;
import com.google.android.material.button.MaterialButton;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements MovieClickListener, Injectable {
    @Inject
    ViewModelFactory viewModelFactory;

    private NowPlayingAdapter nowPlayingAdapter;

    private TextView nowPlayingLabel;

    static NowPlayingFragment newInstance() {
        return new NowPlayingFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_now_playing, container, false);
        nowPlayingAdapter = new NowPlayingAdapter(this, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.nowPlaying_snap);
        nowPlayingLabel = view.findViewById(R.id.now_playing_label);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(nowPlayingAdapter);
        Log.d("Popular", "in OnCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NowPlayingViewModel nowPlayingViewModel = ViewModelProviders.of(this, viewModelFactory).get(NowPlayingViewModel.class);
        observeViewModel(nowPlayingViewModel);
    }


    private void observeViewModel(NowPlayingViewModel mViewModel) {
        mViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                nowPlayingAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d("Now Playing ", "Clicked on: " + title);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        MaterialButton viewAllButton = view.findViewById(R.id.now_all_button);
        viewAllButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ViewAllActivity.class);
            intent.putExtra("now_playing_list", Constants.NOW_PLAYING_LIST);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), nowPlayingLabel, ViewCompat.getTransitionName(nowPlayingLabel));
            startActivity(intent, optionsCompat.toBundle());
        });
    }
}
