package com.hCue.Kiosk.services;


import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;

/**
 * Created by cvluser on 27-07-2015.
 */
public class RestClient {
    private static WebAPI webAPI;

//    static {
//        setupRestClient();
//    }

    //
    private RestClient() {
    }

    /**
     * Method to create and return web api
     *
     * @return
     */
    public static WebAPI getAPI(String url)
    {
        setupRestClient(url);
        return webAPI;
    }

    /*
    creating the rest adapter with required configuration
     */
    private static void setupRestClient(String url) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(15000/*CONNECTION_TIME_OUT*/, TimeUnit.MILLISECONDS);
        okHttpClient.setWriteTimeout(15000/*WRITE_TIME_OUT*/, TimeUnit.MILLISECONDS);
        okHttpClient.setReadTimeout(15000/*READ_TIME_OUT*/, TimeUnit.MILLISECONDS);

        RestAdapter.Builder builder = new RestAdapter.Builder();
        builder.setEndpoint(url/*BASE_URL*/);
        builder.setLogLevel(RestAdapter.LogLevel.FULL);
        builder.setClient(new OkClient(okHttpClient));

        RestAdapter restAdapter = builder.build();
        webAPI = restAdapter.create(WebAPI.class);
    }

}
