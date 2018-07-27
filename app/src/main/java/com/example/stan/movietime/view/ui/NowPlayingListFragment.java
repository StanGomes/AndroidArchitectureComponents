package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.utils.ListItemSpacingDecoration;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.view.adapter.NowPlayingListAdapter;
import com.example.stan.movietime.viewModel.NowPlayingViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class NowPlayingListFragment extends Fragment implements MovieClickListener, Injectable {

    @Inject
    ViewModelFactory viewModelFactory;
    private NowPlayingListAdapter nowPlayingListAdapter;

    public static NowPlayingListFragment newInstance() {
        return new NowPlayingListFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_playing_list, container, false);
        nowPlayingListAdapter = new NowPlayingListAdapter(this, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.now_vertical_list_rv);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(linearLayoutManager);
        ListItemSpacingDecoration itemSpacingDecoration = new ListItemSpacingDecoration(getResources().getDimensionPixelSize(R.dimen.item_offset));
        recyclerView.addItemDecoration(itemSpacingDecoration);
        recyclerView.setAdapter(nowPlayingListAdapter);
        Log.d("NOW PLAYING LIST", "in OnCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NowPlayingViewModel nowPlayingViewModel = ViewModelProviders.of(this, viewModelFactory).get(NowPlayingViewModel.class);
        observeViewModel(nowPlayingViewModel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void observeViewModel(NowPlayingViewModel nowPlayingViewModel) {
        nowPlayingViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                nowPlayingListAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title) {
        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        this.startActivity(intent);
        Log.d("Now Playing List ", "Clicked on: " + title);
    }
}
