package com.raids.gallery.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.raids.gallery.model.Repository;
import com.raids.gallery.model.database.datamodel.FavouriteHit;

import java.util.List;

public class FavouriteViewModel extends AndroidViewModel {

    private Repository repository;

    public FavouriteViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application.getApplicationContext());
    }

    public void refreshData() {
        repository.getAllFavourite();
    }

    public LiveData<List<FavouriteHit>> getFavouriteLive() {
        return repository.getFavourite();
    }
}
