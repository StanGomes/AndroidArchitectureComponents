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
import com.example.stan.movietime.view.MovieClickListener;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class UpcomingListAdapter extends RecyclerView.Adapter<UpcomingListAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private Resource<List<UpcomingEntity>> upcomingEntity;


    public UpcomingListAdapter(MovieClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public UpcomingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item, parent, false);
        return new UpcomingListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UpcomingListAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .load(Constants.POSTER_PATH + upcomingEntity.data.get(holder.getAdapterPosition()).getPosterPath())
                .apply(requestOptions)
                .into(holder.poster);

        holder.title.setText(upcomingEntity.data.get(holder.getAdapterPosition()).getTitle());

        ViewCompat.setTransitionName(holder.posterCard, upcomingEntity.data.get(holder.getAdapterPosition()).getPosterPath());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int movieId = upcomingEntity.data.get(position).getId();
                String title = upcomingEntity.data.get(position).getTitle();
                movieClickListener.onItemClickListener(movieId, title, holder.title, holder.posterCard);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (upcomingEntity.data != null) {
            return upcomingEntity.data.size();
        } else return 0;
    }

    public void setMovies(Resource<List<UpcomingEntity>> moviesEntities) {
        this.upcomingEntity = moviesEntities;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private ImageView poster;
        private MaterialCardView posterCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.list_title);
            poster = itemView.findViewById(R.id.poster_iView);
            posterCard = itemView.findViewById(R.id.vertical_item_card);
        }
    }
}
