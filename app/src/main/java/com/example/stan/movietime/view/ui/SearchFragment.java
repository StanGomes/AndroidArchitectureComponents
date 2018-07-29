package com.example.stan.movietime.view.ui;


import android.app.Dialog;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.utils.SearchItemDecoration;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.viewModel.DiscoverViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends DialogFragment implements MovieClickListener, Injectable {

    private final static String TAG = SearchFragment.class.getSimpleName();
    @Inject
    ViewModelFactory viewModelFactory;
    private String query;
    private FrameLayout frameLayout;
    private com.example.stan.movietime.view.adapter.SearchAdapter searchAdapter;
    private ProgressBar progressBar;

    public static SearchFragment newInstance(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        frameLayout = view.findViewById(R.id.search_layout);
        progressBar = view.findViewById(R.id.search_progress);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.search_recyclerView);
        DiscoverViewModel discoverViewModel = ViewModelProviders.of(this, viewModelFactory).get(DiscoverViewModel.class);
        initRecyclerView(recyclerView);
        observeViewModel(discoverViewModel);
    }

    private void observeViewModel(DiscoverViewModel discoverViewModel) {
        if (getArguments() != null) {
            query = getArguments().getString("query");
        }
        discoverViewModel.searchMovie(query).observe(this, searchResults -> {
            if (searchResults != null) {
                progressBar.setVisibility(View.GONE);
                searchAdapter.setMovies(searchResults);
            }
        });
    }

    private void initRecyclerView(RecyclerView recyclerView) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        SearchItemDecoration searchItemDecoration = new SearchItemDecoration(getResources().getDimensionPixelSize(R.dimen.search_item_offset));
        recyclerView.addItemDecoration(searchItemDecoration);
        searchAdapter = new com.example.stan.movietime.view.adapter.SearchAdapter(this, getContext(), frameLayout);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            Objects.requireNonNull(dialog.getWindow()).setLayout(width, height);
        }
    }


    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d(TAG, "Clicked on: " + title);
    }
}
