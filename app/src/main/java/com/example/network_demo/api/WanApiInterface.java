package com.example.network_demo.api;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;

public interface WanApiInterface {
    @GET("banner/json")
    Observable<ResultBean> getBanner();
}
