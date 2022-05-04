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
import com.example.team7_app.Model.User;

import java.util.ArrayList;
import java.util.List;

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
        etUsername = findViewById(R.id.a_login_et_user  );
        etPass = findViewById(R.id.a_login_et_password );

        btnLogin = findViewById(R.id.a_login_btn_login);
        mListUser = new ArrayList<>();
        getListUsers();
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
             }
        });
//
//        APIService.apiService.getListUsers()
//                .enqueue(new Callback<List<User>>() {
//                    @Override
//                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
//                        Log.i("TEAM8", response.toString());
//                        Log.e("TEAM8", mListUser.size()+ mListUser.toString());
//                        Toast.makeText(LoginActivity.this, "Call API success", Toast.LENGTH_SHORT).show();
//                        mListUser = response.body();
//                    }
//
//                    @Override
//                    public void onFailure(Call<List<User>> call, Throwable t) {
//                        Toast.makeText(LoginActivity.this, "Call API error", Toast.LENGTH_SHORT).show();
//                    }
//                });
    }

    private void clickLogin() {
        String username = etUsername.getText().toString().trim();
        String password= etPass.getText().toString().trim();
        
        if(mListUser==null || mListUser.isEmpty())
        {
            return;
        }

        boolean isHasUser= false;
        for(User usr: mListUser)
        {
            if(username.equals(usr.getLogin())){
                isHasUser = true;
                mUser = usr;
                break;
            }
        }

        if(isHasUser){
             //MainAct
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            Bundle bundle= new Bundle();
            bundle.putSerializable("object_user", mUser);
            intent.putExtras(bundle);
            startActivity(intent);
        }
        else{
            Toast.makeText(LoginActivity.this, "Wrong email or password", Toast.LENGTH_SHORT).show();

        }
    }


    // bam nut back thi ket thuc this, quay lai activity call this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }




}