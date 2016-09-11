package com.example.digsan.githubrx;

import android.content.Intent;

import com.example.digsan.githubrx.models.SearchResponse;
import com.example.digsan.githubrx.models.UserResponse;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by digsan on 09.09.2016.
 */
public interface GitHubService {
    String host = "https://api.github.com/";

    @GET("search/repositories?")
    Observable<SearchResponse> getSearchData(@Query("q") String search, @Query("per_page")Integer countElements);

    @GET("users/{user}")
    Observable<UserResponse> getUser(@Path("user") String userLogin);


}
