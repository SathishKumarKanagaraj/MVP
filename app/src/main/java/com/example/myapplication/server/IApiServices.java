package com.example.myapplication.server;

import com.example.myapplication.Constants;
import com.example.myapplication.addRoles.ResponseValues;
import com.example.myapplication.login.LoginUser;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface IApiServices {
    /**For Login*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<LoginUser> postLoginReq(@Field("module") String module, @Field("uname") String uname, @Field("pass") String pass);

    /**For Adding Roles*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<ResponseValues> postRoles(@Field("module") String module,@Field("data") String data);

    /**For Getting Hod List*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<LoginUser> getHodList(@Field("module") String module,@Field("userid") String userid);

    /**For Leave Request*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<ResponseValues> postLeaveReq(@Field("module") String module,@Field("data") String data);

    /**For Change Password*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<ResponseValues> changePassword(@Field("module") String module,@Field("data") String data);

    /**For Getting Hod List*/
    @FormUrlEncoded
    @POST(Constants.Webservice)
    Call<LoginUser> getLeaveApproveList(@Field("module") String module,@Field("userid") String userid);
}
