package com.example.team7_app.API;

import com.example.team7_app.Model.User;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface APIService {
    //LINK get: https://api-nhom7.herokuapp.com/users
    //LINK post: https://jsonplaceholder.typicode.com/posts

    Gson gson= new GsonBuilder()
            .setDateFormat("yyyy-mm-dd HH:mm:ss")
            .create();
    APIService apiService = new Retrofit.Builder()
            .baseUrl("https://api-nhom7.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(APIService.class);
    @GET("users")
    Call<List<User>> getListUsers();


    @POST("users")
    Call<User> sendUser();

}


