package com.ynby.network.interceptor;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

public class CommonResponseInterceptor implements Interceptor {
    private static final String TAG = "ResponseInterceptor";

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        long requestTime = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        Log.d(TAG, "requestTime = " + (System.currentTimeMillis() - requestTime));
        return response;
    }
}
