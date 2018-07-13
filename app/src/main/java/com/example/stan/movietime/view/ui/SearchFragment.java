package com.example.stan.movietime.view.ui;

import android.app.Dialog;
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
import com.example.stan.movietime.view.adapter.MovieClickListener;
import com.example.stan.movietime.view.adapter.SearchAdapter;
import com.example.stan.movietime.viewModel.SearchViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import java.util.Objects;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


public class SearchFragment extends DialogFragment implements MovieClickListener, Injectable {

    private final static String TAG = SearchFragment.class.getSimpleName();
    @Inject
    ViewModelFactory viewModelFactory;
    private String query;
    private FrameLayout linearLayout;
    private SearchAdapter searchAdapter;
    private ProgressBar progressBar;

    static SearchFragment newInstance(String query) {
        SearchFragment searchFragment = new SearchFragment();
        Bundle args = new Bundle();
        args.putString("query", query);
        searchFragment.setArguments(args);
        return searchFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container);
        linearLayout = view.findViewById(R.id.search_layout);
        progressBar = view.findViewById(R.id.search_progress);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final RecyclerView recyclerView = view.findViewById(R.id.search_recyclerView);
        SearchViewModel searchViewModel = ViewModelProviders.of(this, viewModelFactory).get(SearchViewModel.class);
        initRecyclerView(recyclerView);
        observeViewModel(searchViewModel);
    }

    private void observeViewModel(SearchViewModel searchViewModel) {
        if (getArguments() != null) {
            query = getArguments().getString("query");
        }
        searchViewModel.searchMovie(query).observe(this, searchResults -> {
            if (searchResults != null) {
                progressBar.setVisibility(View.GONE);
                searchAdapter.setMovies(searchResults);
            }
        });
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

    private void initRecyclerView(RecyclerView recyclerView) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        searchAdapter = new SearchAdapter(this, getContext(), linearLayout);
        recyclerView.setAdapter(searchAdapter);
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d(TAG, "Clicked on: " + title);
    }

}
