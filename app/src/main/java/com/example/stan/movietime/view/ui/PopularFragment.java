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
import com.example.stan.movietime.view.adapter.PopularAdapter;
import com.example.stan.movietime.viewModel.PopularViewModel;
import com.example.stan.movietime.viewModel.ViewModelFactory;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.card.MaterialCardView;

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
public class PopularFragment extends Fragment implements MovieClickListener, Injectable {
    @Inject
    ViewModelFactory viewModelFactory;

    private PopularAdapter testAdapter;
    private TextView popularLabel;

    static PopularFragment newInstance() {
        return new PopularFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_popular, container, false);
        testAdapter = new PopularAdapter(this, getContext());
        RecyclerView recyclerView = view.findViewById(R.id.popular_snap);
        popularLabel = view.findViewById(R.id.popular_label);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(testAdapter);
        Log.d("Popular", "in OnCreateView");
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PopularViewModel popularViewModel = ViewModelProviders.of(this, viewModelFactory).get(PopularViewModel.class);
        observeViewModel(popularViewModel);
    }

    private void observeViewModel(PopularViewModel mViewModel) {
        mViewModel.getMovies().observe(this, movieResults -> {
            if (movieResults != null) {
                testAdapter.setMovies(movieResults);
            }
        });
    }

    @Override
    public void onItemClickListener(int movieId, String title, TextView sharedTextView, MaterialCardView sharedImageView) {
        Log.d("Popular ", "Clicked on: " + title);
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MaterialButton viewAllButton = view.findViewById(R.id.popular_all_button);
        viewAllButton.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), ViewAllActivity.class);
            intent.putExtra("popular_list", Constants.POPULAR_LIST);
            ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), popularLabel, ViewCompat.getTransitionName(popularLabel));
            startActivity(intent, optionsCompat.toBundle());
        });
    }

}
