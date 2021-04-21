package com.ynby.network;

import com.ynby.network.base.NetworkApi;
import com.ynby.network.beans.BYResponse;
import com.ynby.network.errorhandler.ExceptionHandle;

import io.reactivex.rxjava3.functions.Function;
import okhttp3.Interceptor;

public class BYNetworkApi extends NetworkApi {
    private static volatile BYNetworkApi sInstance;

    public static BYNetworkApi getInstance() {
        if (sInstance == null) {
            synchronized (BYNetworkApi.class) {
                sInstance = new BYNetworkApi();
            }
        }
        return sInstance;
    }

    protected BYNetworkApi() {
        super("https://www.wanandroid.com/");
    }

    public static <T> T getService(Class<T> service) {
        return getInstance().getRetrofit(service).create(service);
    }


    @Override
    protected Interceptor getInterceptor() {
//        return new Interceptor() {
//            @NotNull
//            @Override
//            public Response intercept(@NotNull Chain chain) throws IOException {
//                Request.Builder builder = chain.request().newBuilder();
//                builder.addHeader("Source", "source");
//                return chain.proceed(builder.build());
//            }
//        };
        return null;
    }

    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return new Function<T, T>() {
            @Override
            public T apply(T response) throws Exception {
                if (response instanceof BYResponse && ((BYResponse) response).errorCode != 0) {
                    ExceptionHandle.ServerException exception = new ExceptionHandle.ServerException();
                    exception.code = ((BYResponse) response).errorCode;
                    exception.message = ((BYResponse) response).errorMsg != null ? ((BYResponse) response).errorMsg : "";
                    throw exception;
                }
                return response;
            }
        };
    }
}
