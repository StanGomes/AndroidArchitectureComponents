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
import com.example.stan.movietime.model.network.model.Cast;
import com.example.stan.movietime.utils.Constants;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

/*************************
 *Author : Stanley Gomes *
 *************************/
public class CastAdapter extends RecyclerView.Adapter<CastAdapter.ViewHolder> {

    private List<Cast> casts;
    private Context context;

    public CastAdapter(List<Cast> casts, Context context) {
        this.casts = casts;
        this.context = context;
    }

    @NonNull
    @Override
    public CastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cast_item, parent, false);
        return new CastAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastAdapter.ViewHolder holder, int position) {
        RequestOptions requestOptions = new RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        if (casts == null || casts.isEmpty()) {
            Log.d("cast_adapter", "empty list");
        } else {
            Glide.with(context)
                    .load(Constants.POSTER_PATH + casts.get(holder.getAdapterPosition()).getProfilePath())
                    .apply(requestOptions)
                    .into(holder.profileImage);
            holder.name.setText(casts.get(holder.getAdapterPosition()).getName());
            holder.character.setText(casts.get(holder.getAdapterPosition()).getCharacter());
        }
    }

    @Override
    public int getItemCount() {
        if (casts != null) {
            return casts.size();
        } else return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, character;
        private ImageView profileImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cast_name);
            character = itemView.findViewById(R.id.character_tv);
            profileImage = itemView.findViewById(R.id.cast_iView);
        }
    }
}
