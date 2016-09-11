package com.example.digsan.githubrx;

import android.app.Application;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by digsan on 10.09.2016.
 */
public class Service extends Application {
    private static Service ourInstance = new Service();

    public static Service getInstance() {
        return ourInstance;
    }

    public final GitHubService gitHubService = new Retrofit.Builder()
            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(GitHubService.host)
            .build().create(GitHubService.class);

    private Service() {
    }
}
