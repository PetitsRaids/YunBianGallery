package com.raids.gallery.utils.network;

import android.util.Log;

import com.raids.gallery.model.database.datamodel.Hit;
import com.raids.gallery.model.database.datamodel.PictureBackData;
import com.raids.gallery.utils.MyUtils;
import com.raids.gallery.utils.service.BackService;
import com.raids.gallery.utils.service.HitsService;

import java.util.List;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RequestNetwork {

    private static RequestNetwork INSTANCE;
    private static Retrofit mRetrofit;
    private static final Object LOCK = new Object();
    private static OkHttpClient client;

    private RequestNetwork() {
        this(new OkHttpClient());
    }

    private RequestNetwork(OkHttpClient client) {
        mRetrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(MyUtils.BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RequestNetwork getInstance() {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                if (INSTANCE == null) {
                    INSTANCE = new RequestNetwork();
                }
            }
        }
        return INSTANCE;
    }

    private HitsService request() {
        return mRetrofit.create(HitsService.class);
    }

    public Call<PictureBackData> getPictures() {
        String type = MyUtils.getRandomImageType();
        Log.d(MyUtils.TAG, type);
        Call<PictureBackData> pictures = request().getPictures(type);
        return pictures;
    }

    public void getHits(BaseObserver<List<Hit>> hitObserverList) {
        BackService backService = mRetrofit.create(BackService.class);
        String type = MyUtils.getRandomImageType();
        Log.d(MyUtils.TAG, "type = " + type);
        backService.getPictures(type)
                .map(PictureBackData::getHits)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .subscribe(hitObserverList);
//        Log.d(MyUtils.TAG, "getHits\nBackService = " + backService + ". pictures = " + pictures);
//        return pictures;
    }

    public static class BaseObserver<T> implements Observer<T> {

        @Override
        public void onSubscribe(Disposable d) {

        }

        @Override
        public void onNext(T t) {

        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onComplete() {

        }
    }

//    public interface RxJavaBackListener {
//        public void onSubscribe(Disposable d);
//        public void
//    }
}
