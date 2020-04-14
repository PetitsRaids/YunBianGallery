package com.raids.gallery.model.adapter;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.adapter.FragmentViewHolder;

import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.utils.MyUtils;
import com.raids.gallery.view.fragment.PagerFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPager2Adapter extends FragmentStateAdapter {

    private List<Hit> hitList;
    private List<PagerFragment> pagerFragments;
    private PagerFragment pagerFragment;

    public ViewPager2Adapter(@NonNull Fragment fragment) {
        super(fragment);
        hitList = new ArrayList<>();
        pagerFragments = new ArrayList<>();
    }

    public void submitList(List<Hit> hitList) {
        if (hitList != null && hitList.size() > 0) {
            this.hitList.clear();
            this.hitList.addAll(hitList);
        }
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        PagerFragment pagerFragment = new PagerFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(MyUtils.BUNDLE_HIT, hitList.get(position));
        bundle.putInt(MyUtils.BUNDLE_POSITION, position);
        pagerFragment.setArguments(bundle);
        this.pagerFragment = pagerFragment;
        if (!pagerFragments.contains(pagerFragment)) {
            pagerFragments.add(pagerFragment);
        }
        Log.d(MyUtils.TAG, "Size = " + pagerFragments.size());
        return pagerFragment;
    }

    @Override
    public int getItemCount() {
        return hitList.size();
    }

    @Override
    public void onBindViewHolder(@NonNull FragmentViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
        Log.d(MyUtils.TAG, "onBindViewHolder position = " + position);
    }

    public PagerFragment getPagerFragment() {
        return pagerFragment;
    }

    public Fragment getFragmentFromPosition(int position) {
        return pagerFragments.get(position);
    }

}
