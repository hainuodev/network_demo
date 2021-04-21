package com.ynby.network.base;

import com.ynby.network.errorhandler.HttpErrorHandler;
import com.ynby.network.interceptor.CommonRequestInterceptor;
import com.ynby.network.interceptor.CommonResponseInterceptor;

import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.ObservableTransformer;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class NetworkApi {
//    private static final String WAN_BASE_URL = "https://www.wanandroid.com/";
//    private static final String TAG = "NetworkApi";

    private static INetworkRequiredInfo iNetworkRequiredInfo;
    private static HashMap<String, Retrofit> retrofitHashMap = new HashMap<>();
    private String mBaseUrl;
    private OkHttpClient mOkHttpClient;

    protected NetworkApi(String baseUrl) {
        mBaseUrl = baseUrl;
    }

    public static void init(INetworkRequiredInfo networkRequiredInfo) {
        iNetworkRequiredInfo = networkRequiredInfo;
    }

    protected Retrofit getRetrofit(Class service) {
        if (retrofitHashMap.get(mBaseUrl + service.getName()) != null) {
            return retrofitHashMap.get(mBaseUrl + service.getName());
        }
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(mBaseUrl);
        builder.client(getOkHttpClient());
        builder.addConverterFactory(GsonConverterFactory.create());
        builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create());
        Retrofit retrofit = builder.build();
        retrofitHashMap.put(mBaseUrl + service.getName(), retrofit);
        return retrofit;
    }

    private OkHttpClient getOkHttpClient() {
        if (mOkHttpClient == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            if (getInterceptor() != null) {
                builder.addInterceptor(getInterceptor());
            }
            builder.addInterceptor(new CommonRequestInterceptor(iNetworkRequiredInfo));
            builder.addInterceptor(new CommonResponseInterceptor());
            if (iNetworkRequiredInfo != null && iNetworkRequiredInfo.isDebug()) {
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                builder.addInterceptor(httpLoggingInterceptor);
            }
            mOkHttpClient = builder.build();
        }
        return mOkHttpClient;
    }

    public <T> ObservableTransformer<T, T> applySchedulers(final Observer<T> observer) {
        return new ObservableTransformer<T, T>() {
            @Override
            public @NonNull ObservableSource<T> apply(@NonNull Observable<T> upstream) {
                Observable<T> observable = upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread()).map(getAppErrorHandler())
                        .onErrorResumeNext(new HttpErrorHandler<T>());
                observable.subscribe(observer);
                return observable;
            }
        };
    }

    protected abstract Interceptor getInterceptor();

    protected abstract <T> Function<T, T> getAppErrorHandler();

}
