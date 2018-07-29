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
import com.example.stan.movietime.model.db.entity.NowPlayingEntity;
import com.example.stan.movietime.model.network.model.Resource;
import com.example.stan.movietime.utils.Constants;
import com.example.stan.movietime.view.MovieClickListener;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class NowPlayingListAdapter extends RecyclerView.Adapter<NowPlayingListAdapter.ViewHolder> {

    private final MovieClickListener movieClickListener;
    private Context mContext;
    private Resource<List<NowPlayingEntity>> nowPlayingList;


    public NowPlayingListAdapter(MovieClickListener movieClickListener, Context context) {
        this.movieClickListener = movieClickListener;
        this.mContext = context;
    }

    @NonNull
    @Override
    public NowPlayingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vertical_item, parent, false);
        return new NowPlayingListAdapter.ViewHolder(view, movieClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull NowPlayingListAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext)
                .load(Constants.POSTER_PATH + nowPlayingList.data.get(holder.getAdapterPosition()).getPosterPath())
                .apply(requestOptions)
                .into(holder.poster);

        holder.title.setText(nowPlayingList.data.get(holder.getAdapterPosition()).getTitle());
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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title;
        private ImageView poster;
        private MovieClickListener movieClickListener;

        public ViewHolder(@NonNull View itemView, MovieClickListener movieClickListener) {
            super(itemView);
            this.movieClickListener = movieClickListener;
            title = itemView.findViewById(R.id.list_title);
            poster = itemView.findViewById(R.id.poster_iView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int movieId = nowPlayingList.data.get(getAdapterPosition()).getId();
            String title = nowPlayingList.data.get(getAdapterPosition()).getTitle();
            movieClickListener.onItemClickListener(movieId, title);
        }
    }
}
