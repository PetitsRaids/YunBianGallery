package com.raids.gallery.model.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.raids.gallery.R;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.utils.MyUtils;

import java.util.ArrayList;

import io.supercharge.shimmerlayout.ShimmerLayout;

public class PicturesAdapter extends ListAdapter<Hit, PicturesAdapter.ViewHolder> {

    private static DiffUtil.ItemCallback<Hit> diff = new DiffUtil.ItemCallback<Hit>() {
        @Override
        public boolean areItemsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull Hit oldItem, @NonNull Hit newItem) {
            return oldItem.getId() == newItem.getId();
        }
    };

    private Context context;

    public PicturesAdapter(Context context) {
        super(diff);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.gallery_cell, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.imageView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            ArrayList<Hit> hits = new ArrayList<>(getCurrentList());
            bundle.putParcelableArrayList(MyUtils.LIST_PARCEL, hits);
            bundle.putInt(MyUtils.PHOTO_POSITION, viewHolder.getAdapterPosition());
            Log.d(MyUtils.TAG, "" + viewHolder.getAdapterPosition());
            Navigation.findNavController(v).navigate(R.id.action_mainFragment_to_pagerFragment, bundle);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.shimmerLayout.setShimmerColor(0x55FFFFFF);
        holder.shimmerLayout.setShimmerAngle(0);
        holder.shimmerLayout.startShimmerAnimation();
        ViewGroup.LayoutParams layoutParams = holder.imageView.getLayoutParams();
        layoutParams.height = getItem(position).getPreviewHeight() * 3;
        holder.imageView.setLayoutParams(layoutParams);
        Glide.with(holder.imageView).load(getItem(position).getPreviewURL()).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d(MyUtils.TAG, "Failed");
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                if (holder.shimmerLayout != null) {
                    holder.shimmerLayout.stopShimmerAnimation();
                }
                return false;
            }
        }).placeholder(R.drawable.ic_loading).into(holder.imageView);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        ShimmerLayout shimmerLayout;
        ViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.preViewImage);
            shimmerLayout = view.findViewById(R.id.shimmerLayout);
        }
    }
}
