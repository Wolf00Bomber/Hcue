package com.appdest.hcue.services;


import com.appdest.hcue.services.WebAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit.RestAdapter;
import retrofit.client.OkClient;
import retrofit.converter.ConversionException;
import retrofit.converter.Converter;
import retrofit.mime.TypedInput;
import retrofit.mime.TypedOutput;

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
