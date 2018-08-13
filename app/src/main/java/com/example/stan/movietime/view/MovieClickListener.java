package com.example.stan.movietime.view;

import android.widget.TextView;

import com.google.android.material.card.MaterialCardView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public interface MovieClickListener {
    void onItemClickListener(int movieId, String title, TextView sharedTextView, MaterialCardView sharedImageView);
}