package com.example.myapplication.http;

import com.example.myapplication.Model.BeiJingTime;
import com.example.myapplication.Model.HomeBean;
import com.example.myapplication.Model.ResponseBean;
import com.example.myapplication.Model.Teacher;
import com.example.myapplication.Model.Token;
import com.example.myapplication.Model.ZhuangbiImage;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface MyService {
    @POST("register")
    Observable<ResponseBean<ZhuangbiImage>> register(
            @Query("username") String username,
            @Query("password") String password
    );
    @POST("getToken")
    Observable<ResponseBean<Token>> getToken(

    );

    @POST("stutea")
    Observable<Teacher> login(

            @Query("tid") int tid
    );



    @GET("api.php?action=api.system.getSearchRes")
    Observable<ResponseBean<HomeBean>> searchSth(@Query("keyword") String keyword);

    @GET("/")
    Observable<ResponseBean<BeiJingTime>> getTime(
            @Query("app") String app,@Query("appkey") String appkey,@Query("sign") String sign,@Query("format") String json);
}
