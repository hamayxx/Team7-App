package com.example.team7_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.team7_app.API.APIService;
import com.example.team7_app.API.ServiceGenerator;
import com.example.team7_app.Model.LoginAuthenticateDTO;
import com.example.team7_app.Model.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final static String TAG = "TEAM8_DEBUGGER";
    CardView btnLogin;
    View vToForgot, vToSignUp;
    //login
    private EditText etUsername;
    private EditText etPass;
    private List<User> mListUser;
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //getSupportActionBar().hide();
        etUsername = findViewById(R.id.a_login_et_user  );
        etPass = findViewById(R.id.a_login_et_password );

        btnLogin = findViewById(R.id.a_login_btn_login);
        mListUser = new ArrayList<>();
//        getListUsers();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
        // cải tiến mới thử xem
       // forget password
        vToForgot = findViewById(R.id.a_login_tv_forget_pass);
        vToForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, EnterMailActivity.class);
                startActivity(intent);
            }
        });
        // signup
        vToSignUp= findViewById(R.id.a_login_tv_signup);
        vToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.clear();
    }
    //login
    private void getListUsers() {
        Log.i("TEAM8", "Getting list users from server!!!");
        APIService loginService = ServiceGenerator.createService(APIService.class, "admin", "admin");
        Call<List<User>> call = loginService.getListUsers();
        call.enqueue(new Callback<List<User>>() {
             @Override
             public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                 if (response.isSuccessful()) {
                     // user object available
                     Log.i(TAG, response.toString());
                     Toast.makeText(LoginActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
                     mListUser = response.body();
                     Log.e(TAG, mListUser.size()+ mListUser.toString());
                 } else {
                     // error response, no access to resource?
                     Log.e(TAG, "LOI CMNR!!!");
                 }
             }

             @Override
             public void onFailure(Call<List<User>> call, Throwable t) {
                 // something went completely south (like no internet connection)
                 Log.e(TAG, t.getMessage());
                 Log.e(TAG, call.toString());
                 Toast.makeText(LoginActivity.this, "CALLED API FAILED!!!", Toast.LENGTH_SHORT).show();
             }
        });

    }

    private void clickLogin() {
        String username = etUsername.getText().toString().trim();
        String password= etPass.getText().toString().trim();

        Log.i("TEAM8", "GET TOKEN FROM SERVER!!!");
        LoginAuthenticateDTO loginUser = new LoginAuthenticateDTO(username, password);
        APIService loginService = ServiceGenerator.createService(APIService.class);

        Call<ResponseBody> call = loginService.getToken(loginUser);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
//                    Log.e(TAG, "response neeeeee: " + new Gson().toJson(response.body()));
                    Toast.makeText(LoginActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
                    String token = "";
                    JSONObject jsonObject = null;
                    try {
                        jsonObject = new JSONObject(response.body().string());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Log.i(TAG, response.toString());

                    try {
                        token = jsonObject.getString("id_token");
                        Log.e(TAG, "Get response token there : " + token);
                        loginUser.setToken(token);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getAccountInfo(loginUser);

                } else {
                    // error response, no access to resource?
                    Log.e(TAG, "FAILED, Status code not 200!!!");
                    Toast.makeText(LoginActivity.this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
                Toast.makeText(LoginActivity.this, "CALLED API FAILED!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getAccountInfo(LoginAuthenticateDTO loginUser) {
        User user = new User();
        user.setToken(loginUser.getToken());

        APIService loginService = ServiceGenerator.createService(APIService.class);
        Call<ResponseBody> getAccountInfoCaller = loginService.getAccountInfo("Bearer " + loginUser.getToken());
        Log.e(TAG, "AUTH HEADER: " + "Bearer " + loginUser.getToken());
        getAccountInfoCaller.enqueue(new Callback<ResponseBody>() {

            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        Log.e(TAG, response.body().string());

                        user.setEmail(jsonObject.getString("email"));
                        user.setLogin(jsonObject.getString("login"));
                        user.setBirth(jsonObject.getString("firstName"));
                        user.setGender(jsonObject.getString("lastName"));

                        Log.e(TAG, "EMAIL NE: " + jsonObject.getString("email"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        Log.e(TAG, "FAILED PARSE OBJECT");
                    }


                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    Bundle bundle= new Bundle();
                    bundle.putSerializable("object_user", user);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    Log.e(TAG, "NOT SUCCESSFULLY");
                    Log.e(TAG, response.toString());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
                Toast.makeText(LoginActivity.this, "Cannot get account info!!!", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // bam nut back thi ket thuc this, quay lai activity call this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}