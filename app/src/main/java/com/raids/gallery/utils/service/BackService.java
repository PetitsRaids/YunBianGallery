package com.raids.gallery.utils.service;

import com.raids.gallery.model.database.datamodel.PictureBackData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface BackService {

    @GET("?key=15700992-1fa27cc7194f2cdf2223345b1&image_type=photo&per_page=100")
    public Observable<PictureBackData> getPictures(@Query("q") String q);
}
