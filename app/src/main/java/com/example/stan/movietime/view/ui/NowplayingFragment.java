package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.view.adapter.MovieClickListener;
import com.example.stan.movietime.view.adapter.NowPlayingAdapter;
import com.example.stan.movietime.viewModel.NowPlayingViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/

public class NowplayingFragment extends Fragment implements MovieClickListener, Injectable {

    private final static String TAG = NowplayingFragment.class.getSimpleName();
    @Inject
    ViewModelFactory viewModelFactory;
    private NowPlayingAdapter mNowPlayingAdapter;

    static NowplayingFragment newInstance() {
        return new NowplayingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nowplaying, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.nowplaying_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        mNowPlayingAdapter = new NowPlayingAdapter(this, getContext());
        recyclerView.setAdapter(mNowPlayingAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        NowPlayingViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory).get(NowPlayingViewModel.class);
        observeViewModel(mViewModel);
    }

    private void observeViewModel(NowPlayingViewModel mViewModel) {
        mViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                mNowPlayingAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d(TAG, "Clicked on: " + title);
    }
}
