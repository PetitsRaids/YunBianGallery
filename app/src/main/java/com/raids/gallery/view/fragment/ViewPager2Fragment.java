package com.raids.gallery.view.fragment;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.raids.gallery.R;
import com.raids.gallery.model.adapter.ViewPager2Adapter;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.model.viewmodel.PagerViewModel;
import com.raids.gallery.utils.MyUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ViewPager2Fragment extends Fragment {

    private List<Hit> hits;
    private ViewPager2 viewPager2;
    private TextView textView;
    private ImageView imageView;
    private ViewPager2Adapter adapter;
    private View.OnLongClickListener longClickListener;
    private PagerViewModel pagerViewModel;
    private int currentPosition;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_pager, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView() {
        viewPager2 = Objects.requireNonNull(getView()).findViewById(R.id.viewPager2);
        textView = Objects.requireNonNull(getView().findViewById(R.id.textView));
        imageView = Objects.requireNonNull(getView().findViewById(R.id.addFavourite));
        adapter = new ViewPager2Adapter(this);
        pagerViewModel = new ViewModelProvider(requireActivity()).get(PagerViewModel.class);

        if (getArguments() != null) {
            hits = getArguments().getParcelableArrayList(MyUtils.LIST_PARCEL);
            adapter.submitList(hits);
        }

        viewPager2.setAdapter(adapter);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                currentPosition = position;
                String text = (position + 1) + "/" + hits.size();
                textView.setText(text);
            }
        });
        viewPager2.setCurrentItem(Objects.requireNonNull(getArguments()).getInt(MyUtils.PHOTO_POSITION), false);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FavouriteHit hit = new FavouriteHit();
                hit.setDate(new Date());
                hit.setHit(getCurrentHit());
                pagerViewModel.addFavouriteHits(hit);
                MyUtils.Toast("已加入收藏");
                pagerViewModel.removeFavourite(hit);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_pager, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.share:
                sharePhoto(getCurrentHit().getLargeImageURL());
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private Hit getCurrentHit() {
        return hits.get(viewPager2.getCurrentItem());
    }

    private void sharePhoto(String uri) {
        Intent share = new Intent(Intent.ACTION_SEND);
        share.putExtra(Intent.EXTRA_TEXT, uri);
        share.setType("text/plain");
        Objects.requireNonNull(getContext()).startActivity(Intent.createChooser(share, "Well played"));
    }
}
