package vn.uit.nt213.m21.team8.file_manager.API;

import vn.uit.nt213.m21.team8.file_manager.Model.ChangePass;
import vn.uit.nt213.m21.team8.file_manager.Model.ChangePassResponse;
import vn.uit.nt213.m21.team8.file_manager.Model.LoginAuthenticateDTO;
import vn.uit.nt213.m21.team8.file_manager.Model.RegisterUserDTO;
import vn.uit.nt213.m21.team8.file_manager.Model.UpdateUserDTO;
import vn.uit.nt213.m21.team8.file_manager.Model.User;

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


    @Headers("Content-Type: application/json")
    @POST("/api/account/change-password")
    Call<ChangePassResponse> changePass(@Header("Authorization") String authHeader, @Body ChangePass changePass);

//    @Headers("Content-Type: application/json")
    @POST("/api/account/reset-password/init")
    Call<String> resetPassword(@Body String email);

}


