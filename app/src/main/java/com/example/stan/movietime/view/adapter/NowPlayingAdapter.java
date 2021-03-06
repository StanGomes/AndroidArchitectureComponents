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
import com.example.stan.movietime.view.MovieClickListener;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class NowPlayingAdapter extends RecyclerView.Adapter<NowPlayingAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private Resource<List<NowPlayingEntity>> nowPlayingList;

    public NowPlayingAdapter(MovieClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NowPlayingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        return new NowPlayingAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (nowPlayingList == null || nowPlayingList.data.isEmpty()) {
            Log.d("nowAdapter", "empty list");
        } else {
            Glide.with(mContext)
                    .load(Constants.BACKDROP_PATH + nowPlayingList.data.get(holder.getAdapterPosition()).getBackdropPath())
                    .apply(requestOptions)
                    .into(holder.backdropImage);
            holder.title.setText(nowPlayingList.data.get(holder.getAdapterPosition()).getTitle());
        }

        ViewCompat.setTransitionName(holder.backdropCard, nowPlayingList.data.get(holder.getAdapterPosition()).getBackdropPath());
//        ViewCompat.setTransitionName(holder.title, nowPlayingList.data.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = nowPlayingList.data.get(position).getId();
                String title = nowPlayingList.data.get(position).getTitle();
                movieClickListener.onItemClickListener(movieId, title, holder.title, holder.backdropCard);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (nowPlayingList.data != null) {
            return 7;
        } else return 0;
    }


    public void setMovies(Resource<List<NowPlayingEntity>> moviesEntities) {
        this.nowPlayingList = moviesEntities;
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
