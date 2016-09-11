package com.example.digsan.githubrx;

import android.app.Application;
import android.util.Log;

/**
 * Created by digsan on 10.09.2016.
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Log.w("MY", "onCreate MyApp");

        //Service.initInstance();
    }
}
