package com.example.team7_app.API;

import com.example.team7_app.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    //LINK get: https://api-nhom7.herokuapp.com/users
    //LINK post: https://jsonplaceholder.typicode.com/post
    // http://34.132.49.139:8080/api/users

    Gson gson= new GsonBuilder()
            .setDateFormat("yyyy-mm-dd HH:mm:ss")
            .create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl("http://34.132.49.139:8080/api/")
            //.baseUrl("https://api-nhom7.herokuapp.com/")
            //.baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
    @GET("users")
    Call<List<User>> getListUsers();


    @POST("register")
    Call<User> createUser(@Body User user);
}


