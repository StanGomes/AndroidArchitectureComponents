package com.example.stan.movietime.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stan.movietime.R;
import com.example.stan.movietime.model.db.entity.SearchEntity;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.view.MovieClickListener;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MovieViewHolder> {
    private static final String TAG = SearchAdapter.class.getSimpleName();
    private final MovieClickListener mMovieClickListener;
    private List<SearchEntity> searchList;
    private Context context;
    private FrameLayout frameLayout;

    public SearchAdapter(MovieClickListener movieClickListener, Context context, FrameLayout frameLayout) {
        Log.d(TAG, "SearchAdapter called");
        this.mMovieClickListener = movieClickListener;
        this.context = context;
        this.frameLayout = frameLayout;
    }

    @NonNull
    @Override
    public SearchAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item, parent, false);
        return new MovieViewHolder(view, mMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.MovieViewHolder holder, int position) {
        RequestOptions posterRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(context)
                .load(Constants.POSTER_PATH + searchList.get(holder.getAdapterPosition()).getPosterPath())
                .apply(posterRequestOptions)
                .into(holder.posterImageView);

        holder.title.setText(searchList.get(holder.getAdapterPosition()).getTitle());
    }

    @Override
    public int getItemCount() {
        if (searchList != null) {
            return searchList.size();
        } else {
            return 0;
        }
    }

    public void setMovies(List<SearchEntity> searchList) {
        if (searchList.isEmpty()) {
            Snackbar.make(frameLayout, "No movies found! :(", Snackbar.LENGTH_SHORT).show();
        }
        this.searchList = searchList;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title;
        ImageView posterImageView;

        private MovieClickListener mListener;

        MovieViewHolder(View itemView, MovieClickListener listener) {
            super(itemView);
            this.mListener = listener;
            posterImageView = itemView.findViewById(R.id.poster_iView);
            title = itemView.findViewById(R.id.list_title);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int movieId = searchList.get(getAdapterPosition()).getId();
            String title = searchList.get(getAdapterPosition()).getTitle();
            mListener.onItemClickListener(movieId, title);
        }
    }
}