package com.ynby.network.interceptor;


import com.ynby.network.base.INetworkRequiredInfo;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class CommonRequestInterceptor implements Interceptor {
    private INetworkRequiredInfo requiredInfo;

    public CommonRequestInterceptor(INetworkRequiredInfo requiredInfo) {
        this.requiredInfo = requiredInfo;
    }

    @NotNull
    @Override
    public Response intercept(@NotNull Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.addHeader("os", "android");
        builder.addHeader("appVersion", requiredInfo.getAppVersionCode());
        return chain.proceed(builder.build());
    }
}
