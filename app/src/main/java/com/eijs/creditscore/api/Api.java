package com.eijs.creditscore.api;


import com.eijs.creditscore.pojo.DefaultResponse;
import com.eijs.creditscore.pojo.EntDocListRes;
import com.eijs.creditscore.pojo.IsUpdateReqRes;
import com.eijs.creditscore.pojo.ListOfEmpRes;

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

    @FormUrlEncoded
    @POST("emplist.php")
    Call<ListOfEmpRes> EmpList(
            @Field("ecode") String ecode,
            @Field("etype") String etype,
            @Field("yr") String yr,
            @Field("mth") String mth
    );

    @FormUrlEncoded
    @POST("doentrydoclist.php")
    Call<EntDocListRes> doEntryDocList(
            @Field("netid") String netid,
            @Field("wrkdate") String date
    );
}
