package com.example.stan.movietime.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.stan.movietime.R;
import com.example.stan.movietime.model.db.entity.UpcomingEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/************************
 *Author : Stanley Gomes *
 *Since : 01/06/2018        *
 ************************/

public class UpcomingAdapter extends RecyclerView.Adapter<UpcomingAdapter.MovieViewHolder> {

    private static final String TAG = UpcomingAdapter.class.getSimpleName();
    private final MovieClickListener clickListener;

    private Resource<List<UpcomingEntity>> upcomingList;

    private Context mContext;

    public UpcomingAdapter(MovieClickListener clickListener, Context context) {
        this.clickListener = clickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UpcomingAdapter.MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_layout, parent, false);
        return new MovieViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingAdapter.MovieViewHolder holder, int position) {
        RequestOptions posterRequestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .load(Constants.POSTER_PATH + upcomingList.data.get(holder.getAdapterPosition()).getPosterPath())
                .apply(posterRequestOptions)
                .into(holder.posterImageView);

        holder.title.setText(upcomingList.data.get(holder.getAdapterPosition()).getTitle());
        holder.date.setText(upcomingList.data.get(holder.getAdapterPosition()).getReleaseDate());
        holder.summary.setText(upcomingList.data.get(holder.getAdapterPosition()).getOverview());
    }

    @Override
    public int getItemCount() {
        if (upcomingList.data != null) {
            return upcomingList.data.size();
        }
        return 0;
    }

    public void setMovies(Resource<List<UpcomingEntity>> moviesEntities) {
        this.upcomingList = moviesEntities;
        notifyDataSetChanged();
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView title, summary, date;
        ImageView posterImageView;
        private MovieClickListener clickListener;

        public MovieViewHolder(@NonNull View itemView, MovieClickListener listener) {
            super(itemView);
            this.clickListener = listener;
            posterImageView = itemView.findViewById(R.id.poster_imageView);
            title = itemView.findViewById(R.id.title_textView);
            summary = itemView.findViewById(R.id.summary_textView);
            date = itemView.findViewById(R.id.date_textView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int movieId = upcomingList.data.get(getAdapterPosition()).getId();
            String title = upcomingList.data.get(getAdapterPosition()).getTitle();
            clickListener.onItemClickListener(movieId, title);
        }
    }
}
