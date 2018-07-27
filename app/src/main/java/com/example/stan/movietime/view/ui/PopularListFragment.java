package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.view.adapter.PopularListAdapter;
import com.example.stan.movietime.viewModel.PopularViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class PopularListFragment extends Fragment implements MovieClickListener, Injectable {

    @Inject
    ViewModelFactory viewModelFactory;
    private Intent intent;
    private PopularListAdapter popularListAdapter;

    public static PopularListFragment newInstance() {
        return new PopularListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popular_list, container, false);
        popularListAdapter = new PopularListAdapter(this, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.popular_vertical_list_rv);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(popularListAdapter);
        Log.d("POPULAR LIST", "in OnCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        PopularViewModel popularViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopularViewModel.class);
        observeViewModel(popularViewModel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void observeViewModel(PopularViewModel popularViewModel) {
        popularViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                popularListAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d("Test Popular List", "Clicked on: " + title);
    }
}
