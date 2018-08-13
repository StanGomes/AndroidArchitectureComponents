package com.example.stan.movietime.view.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.stan.movietime.R;
import com.example.stan.movietime.di.Injectable;
import com.example.stan.movietime.utils.ListItemSpacingDecoration;
import com.example.stan.movietime.view.MovieClickListener;
import com.example.stan.movietime.view.adapter.TopListAdapter;
import com.example.stan.movietime.viewModel.TopViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;
import com.google.android.material.card.MaterialCardView;

import javax.inject.Inject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class TopListFragment extends Fragment implements MovieClickListener, Injectable {

    @Inject
    ViewModelFactory viewModelFactory;
    private TopListAdapter topListAdapter;

    public static TopListFragment newInstance() {
        return new TopListFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postponeEnterTransition();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top_list, container, false);
        topListAdapter = new TopListAdapter(this, getContext());

        Log.d("TOP LIST", "in OnCreateView");
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = view.findViewById(R.id.top_vertical_list_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        recyclerView.setLayoutManager(gridLayoutManager);
        ListItemSpacingDecoration itemSpacingDecoration = new ListItemSpacingDecoration(getResources().getDimensionPixelSize(R.dimen.item_offset));
        recyclerView.addItemDecoration(itemSpacingDecoration);
        recyclerView.setAdapter(topListAdapter);
        TopViewModel topViewModel = ViewModelProviders.of(this, viewModelFactory).get(TopViewModel.class);
        observeViewModel(topViewModel);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void observeViewModel(TopViewModel nowPlayingViewModel) {
        nowPlayingViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                topListAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title, TextView sharedTextView, MaterialCardView sharedImageView) {
        Log.d("Now Playing List", "Clicked on: " + title);
        String posterTransitionName = ViewCompat.getTransitionName(sharedImageView);
        String titleTransitionName = ViewCompat.getTransitionName(sharedTextView);

        Intent intent = new Intent(getContext(), MovieDetailActivity.class);
        intent.putExtra("id", movieId);
        intent.putExtra("poster_transition", posterTransitionName);
        intent.putExtra("title_transition", titleTransitionName);
        Pair<View, String> titlePair = Pair.create(sharedTextView, titleTransitionName);
        Pair<View, String> backdropPair = Pair.create(sharedImageView, posterTransitionName);
        ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), sharedImageView, ViewCompat.getTransitionName(sharedImageView));
        startActivity(intent, optionsCompat.toBundle());
    }

}
