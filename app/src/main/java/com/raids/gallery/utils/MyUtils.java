package com.raids.gallery.utils;

import android.widget.Toast;

import com.raids.gallery.GalleryApplication;

import java.util.Random;

public class MyUtils {

    public static final String LIST_PARCEL = "PAGER";

    public static final String PHOTO_POSITION = "PHOTO_POSITION";
    public static final int STOP_REFRESHING = 9;
    public static final String BUNDLE_HIT = "BUNDLE_HIT";
    public static final String BUNDLE_POSITION = "BUNDLE_POSITION";

    public static String TAG = "GALLERY_TAG";
    public static String KEY = "15700992-1fa27cc7194f2cdf2223345b1";
    public static final String BASE_URL = "https://pixabay.com/api/";
    public static String TEST_URL = "https://pixabay.com/get/55e0d340485aa814f6da8c7dda79367b123fdeec5a536c4870277fdd944fcd58bf_640.jpg";

    public static String getRandomImageType() {
        String[] types = {"backgrounds", "nature", "science", "feelings", "health", "religion", "places", "animals", "industry", "computer", "food", "sports", "transportation", "travel", "music"};
        Random random = new Random();
        return types[random.nextInt(15)];
    }

    public static void Toast(String message) {
        Toast.makeText(GalleryApplication.getContext(), message, Toast.LENGTH_SHORT).show();
    }

    public static void Toast(int resId) {
        Toast.makeText(GalleryApplication.getContext(), resId, Toast.LENGTH_SHORT).show();
    }
}
