package com.raids.gallery.view.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.raids.gallery.model.viewmodel.GalleryViewModel;
import com.raids.gallery.R;
import com.raids.gallery.model.adapter.PicturesAdapter;
import com.raids.gallery.utils.MyUtils;

import java.lang.ref.WeakReference;
import java.util.Objects;

public class MainFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayout;
    private MyHandler handler = new MyHandler(this);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        setHasOptionsMenu(true);
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        PicturesAdapter adapter = new PicturesAdapter(getContext());
        StaggeredGridLayoutManager manager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        RecyclerView recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.recyclerView);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(manager);

        swipeRefreshLayout = getView().findViewById(R.id.swipeRefresh);
        GalleryViewModel galleryViewModel = new ViewModelProvider(requireActivity()).get(GalleryViewModel.class);
        galleryViewModel.getHitsLive().observe(getViewLifecycleOwner(), hits -> {
            Log.d(MyUtils.TAG, "onChanged() and hits = " + hits);
            adapter.submitList(hits);
            swipeRefreshLayout.setRefreshing(false);
        });
        galleryViewModel.setHandler(handler);
        if (galleryViewModel.getHitsLive().getValue() == null) {
            Log.d(MyUtils.TAG, "NULL");
            galleryViewModel.loadData();
            swipeRefreshLayout.setRefreshing(true);
        }
        swipeRefreshLayout.setOnRefreshListener(galleryViewModel::fetchData);
    }

    private void loadFailed() {
        swipeRefreshLayout.setRefreshing(false);
        Toast.makeText(getContext(), R.string.load_failed, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toFavourite:
                Navigation.findNavController(Objects.requireNonNull(getView()))
                        .navigate(R.id.action_mainFragment_to_favouriteFragment);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class MyHandler extends Handler {

        WeakReference<MainFragment> weakFragment;

        MyHandler(MainFragment mainFragment) {
            weakFragment = new WeakReference<>(mainFragment);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MyUtils.STOP_REFRESHING:
                    Log.d(MyUtils.TAG, "handleMessage");
                    weakFragment.get().loadFailed();
                    break;
                default:
                    break;
            }
        }
    }
}
