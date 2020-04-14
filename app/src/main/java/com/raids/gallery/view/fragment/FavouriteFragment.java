package com.raids.gallery.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.raids.gallery.R;
import com.raids.gallery.model.adapter.FavouriteAdapter;
import com.raids.gallery.model.adapter.PicturesAdapter;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.viewmodel.FavouriteViewModel;

import java.util.List;
import java.util.Objects;

public class FavouriteFragment extends Fragment {

    private FavouriteViewModel mViewModel;
    private RecyclerView recyclerView;
    private FavouriteAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.favourite_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.favouriteRecyclerView);
        adapter = new FavouriteAdapter();
        mViewModel = new ViewModelProvider(requireActivity()).get(FavouriteViewModel.class);
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        mViewModel.getFavouriteLive()
                .observe(getViewLifecycleOwner(), favouriteHits -> adapter.submitList(favouriteHits));
        if (mViewModel.getFavouriteLive().getValue() == null) {
            mViewModel.refreshData();
        }
    }

}
