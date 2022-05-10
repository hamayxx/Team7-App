package com.example.team7_app.API;

import com.example.team7_app.Model.RegisterUserDTO;
import com.example.team7_app.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {
    @GET("/api/admin/users")
    Call<List<User>> getListUsers();

    @Headers("Content-Type: application/json")
    @POST("/api/register")
    Call<RegisterUserDTO> createUser(@Body RegisterUserDTO user);
}


