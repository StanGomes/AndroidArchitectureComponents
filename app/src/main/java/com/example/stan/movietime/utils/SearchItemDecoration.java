package com.example.stan.movietime.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class SearchItemDecoration extends RecyclerView.ItemDecoration {

    private int space;

    public SearchItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = space;
        outRect.right = space;

        if (parent.getChildLayoutPosition(view) == 0) {
            outRect.top = 4;
        } else if (parent.getChildLayoutPosition(view) == 1) {
            outRect.top = 4;
        } else {
            outRect.top = 0;
        }

    }
}
