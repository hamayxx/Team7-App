package com.example.team7_app.API;

import com.example.team7_app.Model.LoginAuthenticateDTO;
import com.example.team7_app.Model.RegisterUserDTO;
import com.example.team7_app.Model.UpdateUserDTO;
import com.example.team7_app.Model.User;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @GET("/api/admin/users")
    Call<List<User>> getListUsers();

    @POST("/api/authenticate")
    Call<ResponseBody> getToken(@Body LoginAuthenticateDTO loginUser);

    @Headers("Content-Type: application/json")
    @POST("/api/register")
    Call<RegisterUserDTO> createUser(@Body RegisterUserDTO user);


    @Headers("Content-Type: application/json")
    @GET("/api/account")
    Call<ResponseBody> getAccountInfo(@Header("Authorization") String authHeader);


    @Headers("Content-Type: application/json")
    @POST("/api/account")
    Call<ResponseBody> postAccountInfo(@Header("Authorization") String authHeader, @Body UpdateUserDTO updateUserDTO);
}


