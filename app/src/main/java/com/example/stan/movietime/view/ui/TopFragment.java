package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.view.adapter.MovieClickListener;
import com.example.stan.movietime.view.adapter.TopAdapter;
import com.example.stan.movietime.viewModel.TopViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import java.util.List;

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

public class TopFragment extends Fragment implements MovieClickListener, Injectable {

    private final static String TAG = TopFragment.class.getSimpleName();
    @Inject
    ViewModelFactory viewModelFactory;
    private TopAdapter mMovieAdapter;

    static TopFragment newInstance() {
        return new TopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_fragment, container, false);
        final RecyclerView recyclerView = view.findViewById(R.id.top_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerSpace(4));
        mMovieAdapter = new TopAdapter(this, getContext());
        recyclerView.setAdapter(mMovieAdapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        final TopViewModel mViewModel = ViewModelProviders.of(this, viewModelFactory).get(TopViewModel.class);
        observeViewModel(mViewModel);
    }

    private void observeViewModel(TopViewModel mViewModel) {
        mViewModel.getMovies().observe(this, (Resource<List<TopEntity>> movieResults) -> {
            if (movieResults != null) {
                mMovieAdapter.setMovies(movieResults);
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
