package com.example.team7_app.API;

import com.example.team7_app.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    @GET("/api/admin/users")
    Call<List<User>> getListUsers();


    @POST("register")
    Call<User> createUser(@Body User user);
}


