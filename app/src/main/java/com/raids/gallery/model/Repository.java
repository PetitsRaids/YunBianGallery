package com.raids.gallery.model;

import android.content.Context;
import android.os.Message;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.raids.gallery.model.database.AppDatabase;
import com.raids.gallery.model.database.datamodel.FavouriteHit;
import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.utils.MyUtils;
import com.raids.gallery.utils.network.RequestNetwork;
import com.raids.gallery.view.fragment.MainFragment;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Repository {

    private static Repository INSTANCE;
    private MainFragment.MyHandler handler;
    private AppDatabase database;
    private static final Object LOCK = new Object();
    private MutableLiveData<List<Hit>> hitsLive;
    private MutableLiveData<List<FavouriteHit>> favouriteLive;
    private Executor executor = Executors.newSingleThreadExecutor();

    private Repository(Context context) {
        database = AppDatabase.getInstance(context);
        hitsLive = new MutableLiveData<>();
        favouriteLive = new MutableLiveData<>();
    }

    public static Repository getRepository(Context context) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new Repository(context);
                }
            }
        }
        return INSTANCE;
    }

    public LiveData<List<Hit>> getHitsLive() {
        return hitsLive;
    }

    public void loadData() {
        executor.execute(() -> {
            List<Hit> hitList = database.getHitDao().getAllHits();
            if (hitList != null) {
                hitsLive.postValue(hitList);
            }
        });
    }

    public void fetchData() {
        RequestNetwork.getInstance().getHits(new RequestNetwork.BaseObserver<List<Hit>>() {

            @Override
            public void onNext(List<Hit> hits) {
                hitsLive.postValue(hits);
                database.getHitDao().insertAll(hits);
            }

            @Override
            public void onError(Throwable e) {
                super.onError(e);
                if (handler != null) {
                    Message message = Message.obtain();
                    message.what = MyUtils.STOP_REFRESHING;
                    handler.sendMessage(message);
                }
            }
        });
    }

    public void setHandler(MainFragment.MyHandler handler) {
        this.handler = handler;
    }

    public void saveData() {
        executor.execute(() -> database.getHitDao().insertAll(hitsLive.getValue()));
    }

    public void addToFavourite(FavouriteHit hit) {
        executor.execute(() -> {
            database.getFavouriteHit().insertFavourite(hit);
        });
    }

    public void getAllFavourite() {
        executor.execute(() -> {
            favouriteLive.postValue(database.getFavouriteHit().getAllFavourites());
        });
    }

    public LiveData<List<FavouriteHit>> getFavourite() {
        return favouriteLive;
    }

    public void removeFavourite(FavouriteHit hit) {
        executor.execute(() -> {
            database.getFavouriteHit().removeFavourite(hit);
        });
    }
}
