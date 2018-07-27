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
import com.example.stan.movietime.view.adapter.UpcomingListAdapter;
import com.example.stan.movietime.viewModel.UpcomingViewModel;
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
public class UpcomingListFragment extends Fragment implements MovieClickListener, Injectable {

    @Inject
    ViewModelFactory viewModelFactory;
    private Intent intent;
    private UpcomingListAdapter upcomingListAdapter;

    public static UpcomingListFragment newInstance() {
        return new UpcomingListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.upcoming_list, container, false);
        upcomingListAdapter = new UpcomingListAdapter(this, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.upcoming_vertical_list_rv);
        LinearLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(upcomingListAdapter);
        Log.d("UPCOMING LIST", "in OnCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        UpcomingViewModel upcomingViewModel = ViewModelProviders.of(this, viewModelFactory).get(UpcomingViewModel.class);
        observeViewModel(upcomingViewModel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void observeViewModel(UpcomingViewModel upcomingViewModel) {
        upcomingViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                upcomingListAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d("Upcoming List ", "Clicked on: " + title);
    }
}
