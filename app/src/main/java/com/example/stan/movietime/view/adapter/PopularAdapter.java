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
import com.example.stan.movietime.model.db.entity.PopularEntity;
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
public class PopularAdapter extends RecyclerView.Adapter<PopularAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private Resource<List<PopularEntity>> popularList;

    public PopularAdapter(MovieClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public PopularAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        return new PopularAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (popularList == null || popularList.data.isEmpty()) {
            Log.d("popularAdapter", "empty list");
        } else {
            Glide.with(mContext)
                    .load(Constants.BACKDROP_PATH + popularList.data.get(holder.getAdapterPosition()).getBackdropPath())
                    .apply(requestOptions)
                    .into(holder.backdropImage);
            holder.title.setText(popularList.data.get(holder.getAdapterPosition()).getTitle());
        }
        ViewCompat.setTransitionName(holder.backdropCard, popularList.data.get(holder.getAdapterPosition()).getBackdropPath());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = popularList.data.get(position).getId();
                String title = popularList.data.get(position).getTitle();
                movieClickListener.onItemClickListener(movieId, title, holder.title, holder.backdropCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (popularList.data != null) {
            return 7;
        } else return 0;
    }

    public void setMovies(Resource<List<PopularEntity>> moviesEntities) {
        this.popularList = moviesEntities;
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
