package com.example.hackathon.api;


import java.util.Map;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Url;

public interface RetrofitServiceStore {

    interface GetRetrofitService {
        @GET
        Observable<Response<String>> getCall(@Url String url);
    }

    interface PostRetrofitService {
        @POST
        @FormUrlEncoded
        Observable<Response<String>> postCall(@Url String url, @FieldMap Map<String, String> params);
    }

}

