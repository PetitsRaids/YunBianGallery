package com.raids.gallery.utils.service;

import com.raids.gallery.model.database.datamodel.PictureBackData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface HitsService {
    @GET("?key=15700992-1fa27cc7194f2cdf2223345b1&image_type=photo&per_page=100")
    public Call<PictureBackData> getPictures(@Query("q") String q);
}
