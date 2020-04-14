package com.raids.gallery.utils.network;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkhttpEnterence {
    public static void requestNetworks(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("cn.bing.com")
                .build();
        try {
            Response response = client.newCall(request).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
