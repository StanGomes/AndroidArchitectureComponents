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
import com.example.stan.movietime.model.db.entity.TopEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.view.MovieClickListener;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private Resource<List<TopEntity>> topList;

    public TopAdapter(MovieClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        return new TopAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (topList == null || topList.data.isEmpty()) {
            Log.d("topAdapter", "empty list");
        } else {
            Glide.with(mContext)
                    .load(Constants.BACKDROP_PATH + topList.data.get(holder.getAdapterPosition()).getBackdropPath())
                    .apply(requestOptions)
                    .into(holder.backdropImage);
            holder.title.setText(topList.data.get(holder.getAdapterPosition()).getTitle());
        }
        ViewCompat.setTransitionName(holder.backdropCard, topList.data.get(holder.getAdapterPosition()).getBackdropPath());
//        ViewCompat.setTransitionName(holder.title, nowPlayingList.data.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = topList.data.get(position).getId();
                String title = topList.data.get(position).getTitle();
                movieClickListener.onItemClickListener(movieId, title, holder.title, holder.backdropCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (topList.data != null) {
            return 7;
        } else return 0;
    }

    public void setMovies(Resource<List<TopEntity>> moviesEntities) {
        this.topList = moviesEntities;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView backdropImage;
        private MaterialCardView backdropCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_title);
            backdropImage = itemView.findViewById(R.id.backdrop_ImageView);
            backdropCard = itemView.findViewById(R.id.horizontal_item_card);
        }
    }
}
