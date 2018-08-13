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
import com.example.stan.movietime.model.db.entity.RecommendedEntity;
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
public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private List<RecommendedEntity> recommendedEntity;

    public RecommendedAdapter(MovieClickListener movieClickListener, Context mContext, List<RecommendedEntity> recommendedEntity) {
        this.movieClickListener = movieClickListener;
        this.mContext = mContext;
        this.recommendedEntity = recommendedEntity;
    }


    @NonNull
    @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.horizontal_item, parent, false);
        return new RecommendedAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendedAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (recommendedEntity == null || recommendedEntity.isEmpty()) {
            Log.d("recommended adapter", "empty list");
        } else {
            Glide.with(mContext)
                    .load(Constants.BACKDROP_PATH + recommendedEntity.get(holder.getAdapterPosition()).getBackdropPath())
                    .apply(requestOptions)
                    .into(holder.backdropImage);
            holder.title.setText(recommendedEntity.get(holder.getAdapterPosition()).getTitle());

        }
        ViewCompat.setTransitionName(holder.backdropCard, recommendedEntity.get(holder.getAdapterPosition()).getBackdropPath());
//        ViewCompat.setTransitionName(holder.title, nowPlayingList.data.get(position).getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = recommendedEntity.get(position).getId();
                String title = recommendedEntity.get(position).getTitle();
                movieClickListener.onItemClickListener(movieId, title, holder.title, holder.backdropCard);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (recommendedEntity != null) {
            return recommendedEntity.size();
        } else return 0;
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
