package com.raids.gallery.model.adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.raids.gallery.R;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.utils.MyUtils;

import java.util.ArrayList;
import java.util.List;

public class FavouriteAdapter extends ListAdapter<FavouriteHit, FavouriteAdapter.ViewHolder> {

    public FavouriteAdapter() {
        super(new DiffUtil.ItemCallback<FavouriteHit>() {
            @Override
            public boolean areItemsTheSame(@NonNull FavouriteHit oldItem, @NonNull FavouriteHit newItem) {
                return oldItem.equals(newItem);
            }

            @Override
            public boolean areContentsTheSame(@NonNull FavouriteHit oldItem, @NonNull FavouriteHit newItem) {
                return oldItem.getId() == newItem.getId();
            }
        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourite_cell, parent, false);
        ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Hit> hits = new ArrayList<>();
                for (FavouriteHit hit : getCurrentList()) {
                    hits.add(hit.getHit());
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(MyUtils.LIST_PARCEL, hits);
                bundle.putInt(MyUtils.PHOTO_POSITION, holder.getAdapterPosition());
                Navigation.findNavController(v).navigate(R.id.action_favouriteFragment_to_pagerFragment2, bundle);
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.height = getItem(position).getHit().getPreviewHeight() * 3;
        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(holder.imageView.getContext())
                .load(getItem(position).getHit().getPreviewURL())
                .placeholder(R.drawable.ic_loading)
                .into(holder.imageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView);
        }
    }
}
