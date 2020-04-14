package com.raids.gallery.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.raids.gallery.model.Repository;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.database.datamodel.Hit;

public class PagerViewModel extends AndroidViewModel {

    private Repository repository;

    public PagerViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application.getApplicationContext());
    }

    public void addFavouriteHits(FavouriteHit hit) {
        repository.addToFavourite(hit);
    }

    public void removeFavourite(FavouriteHit hit) {
        repository.removeFavourite(hit);
    }
}
