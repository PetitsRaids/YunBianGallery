package com.raids.gallery.view.fragment;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.raids.gallery.R;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.utils.MyUtils;

import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.util.Objects;

import io.supercharge.shimmerlayout.ShimmerLayout;
import uk.co.senab.photoview.PhotoView;

public class PagerFragment extends Fragment {

    private PhotoView photoView;
    private ShimmerLayout shimmerLayout;
    private Hit hit;
    private Uri shareUri;
    private int position;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.photo_view_pager_cell, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photoView = Objects.requireNonNull(getView()).findViewById(R.id.photoView);
        photoView.setOnLongClickListener(v -> {
            savePhoto();
            return false;
        });
        shimmerLayout = getView().findViewById(R.id.shimmerLayoutPager);
        if (getArguments() != null) {
            hit = getArguments().getParcelable(MyUtils.BUNDLE_HIT);
            position = getArguments().getInt(MyUtils.BUNDLE_POSITION);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        shimmerLayout.setShimmerColor(0x55FFFFFF);
        shimmerLayout.setShimmerAngle(0);
        shimmerLayout.startShimmerAnimation();
        Glide.with(Objects.requireNonNull(getContext()))
                .load(hit.getLargeImageURL())
                .placeholder(R.drawable.ic_photo_gray_24dp)
                .listener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        Log.d(MyUtils.TAG, "Load Failed");
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        shimmerLayout.stopShimmerAnimation();
                        return false;
                    }
                })
                .into(photoView);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (shimmerLayout != null) {
            shimmerLayout.stopShimmerAnimation();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(MyUtils.TAG, "PagerFragment#onDestroy() position = " + position);
    }

    private void savePhoto() {
        ContentResolver contentResolver = requireActivity().getContentResolver();
        shareUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        Thread thread = new Thread(() -> {
            try {
                if (shareUri != null) {
                    OutputStream outputStream;
                    outputStream = contentResolver.openOutputStream(shareUri);
                    Bitmap bitmap = ((BitmapDrawable) photoView.getDrawable()).getBitmap();
                    boolean isCompress = bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    if (isCompress) {
                        requireActivity().runOnUiThread(() -> {
                            MyUtils.Toast("保存成功！");
                        });
                        Log.d(MyUtils.TAG, "hit.width = " + hit.getImageWidth() + " hit.height = " + hit.getImageHeight() + ".");
                    } else {
                        requireActivity().runOnUiThread(() -> {
                            MyUtils.Toast("保存失败！");
                        });
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                requireActivity().runOnUiThread(() -> {
                    MyUtils.Toast("保存失败！");
                });
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public Uri getShareUri() {
        savePhoto();
        return shareUri;
    }
}
