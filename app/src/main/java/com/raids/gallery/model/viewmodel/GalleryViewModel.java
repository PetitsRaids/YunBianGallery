package com.raids.gallery.model.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.raids.gallery.model.Repository;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.view.fragment.MainFragment;

import java.util.List;

public class GalleryViewModel extends AndroidViewModel {

    private Repository repository;

    public GalleryViewModel(@NonNull Application application) {
        super(application);
        repository = Repository.getRepository(application.getApplicationContext());
    }

    public LiveData<List<Hit>> getHitsLive() {
        return repository.getHitsLive();
    }

    public void loadData() {
        repository.loadData();
    }

    public void fetchData() {
        repository.fetchData();
    }

    public void setHandler(MainFragment.MyHandler handler) {
        repository.setHandler(handler);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        repository.saveData();
    }
}
