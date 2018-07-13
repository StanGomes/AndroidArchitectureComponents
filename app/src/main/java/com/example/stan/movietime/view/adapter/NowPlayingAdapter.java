package com.example.stan.movietime.view.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stan.movietime.R;
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/************************
 *Author : Stanley Gomes *
 *Since : 30/05/2018        *
 ************************/
public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.MovieViewHolder> {

    private static final String TAG = NowPlayingAdapter.class.getSimpleName();
    private final MovieClickListener mMovieClickListener;
    private Resource<List<NowPlayingEntity>> nowPlayingList;

    private Context mContext;

    public NowPlayingAdapter(MovieClickListener movieClickListener, Context context) {
        Log.d(TAG, "NowPlayingAdapter called");

        this.mMovieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_layout, parent, false);
        return new MovieViewHolder(view, mMovieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        RequestOptions posterRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .load(Constants.POSTER_PATH + nowPlayingList.data.get(holder.getAdapterPosition()).getPosterPath())
                .apply(posterRequestOptions)
                .into(holder.posterImageView);

        holder.title.setText(nowPlayingList.data.get(holder.getAdapterPosition()).getTitle());
        holder.date.setText(nowPlayingList.data.get(holder.getAdapterPosition()).getReleaseDate());
        holder.summary.setText(nowPlayingList.data.get(holder.getAdapterPosition()).getOverview());
    }

    @Override
    public int getItemCount() {
        if (nowPlayingList.data != null) {
            return nowPlayingList.data.size();
        } else return 0;
    }

    public void setMovies(Resource<List<NowPlayingEntity>> moviesEntities) {
        this.nowPlayingList = moviesEntities;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, summary, date;

        ImageView posterImageView;
        private MovieClickListener mListener;
        MovieViewHolder(View itemView, MovieClickListener listener) {
            super(itemView);
            this.mListener = listener;
            posterImageView = itemView.findViewById(R.id.poster_imageView);
            title = itemView.findViewById(R.id.title_textView);
            summary = itemView.findViewById(R.id.summary_textView);
            date = itemView.findViewById(R.id.date_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int movieId = nowPlayingList.data.get(getAdapterPosition()).getId();
            String title = nowPlayingList.data.get(getAdapterPosition()).getTitle();
            mListener.onItemClickListener(movieId, title);
        }
    }
}
