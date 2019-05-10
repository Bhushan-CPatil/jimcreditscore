package com.eijs.creditscore.api;


import com.eijs.creditscore.pojo.DefaultResponse;
import com.eijs.creditscore.pojo.IsUpdateReqRes;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    //to login
    @FormUrlEncoded
    @POST("login.php")
    Call<DefaultResponse> Login(
            @Field("ecode") String ecode,
            @Field("password") String password,
            @Field("date") String date
    );

    @POST("isupdaterequire.php")
    Call<IsUpdateReqRes> IsUpdateRequired();


}
