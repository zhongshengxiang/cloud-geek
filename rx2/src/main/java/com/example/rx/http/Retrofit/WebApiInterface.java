package com.example.rx.http.Retrofit;

import com.example.rx.model.HomeBean;
import com.example.rx.model.ResponseBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebApiInterface {
    @GET("api.php?action=api.system.getSearchRes")
    Observable<ResponseBean<HomeBean>> searchSth(@Query("keyword") String keyword);
}
