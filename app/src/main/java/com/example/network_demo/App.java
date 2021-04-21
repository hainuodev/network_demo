package com.example.network_demo;

import android.app.Application;

import com.ynby.network.base.NetworkApi;

public class App extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        NetworkApi.init(new NetworkInfo());
    }
}
