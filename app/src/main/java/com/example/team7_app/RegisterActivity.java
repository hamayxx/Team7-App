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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    CardView cvToLogin;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private User mUser ;
    private final static String TAG = "TEAM8_DEBUGGER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        etUsername=findViewById(R.id.a_register_et_user);
        etPassword=findViewById(R.id.a_register_et_password);
        etEmail=findViewById(R.id.a_register_et_mail);



        cvToLogin = findViewById(R.id.a_register_btn_register);
        cvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUp();
            }
        });

    }

    private void createUser() {
        Log.i("TEAM8", "Getting list users from server!!!");
        APIService signupService = ServiceGenerator.createService(APIService.class, "admin", "admin");
        Call<User> call = signupService.createUser(mUser);
        Log.e(TAG, "User API: "+mUser.toString().trim());

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Respone" + response.toString());
                    Toast.makeText(RegisterActivity.this, "Call API <post> success", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                } else {
                    // error response, no access to resource?
                    Log.e(TAG, "SignUp: LOI CMNR!!!"+ response.toString() );
                }

            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
            }
        });

    }

    private void onSignUp() {
        //kiem tra dau vao
        mUser = new User();
        String username = etUsername.getText().toString().trim();
        String password= etPassword.getText().toString().trim();
        String email= etEmail.getText().toString().trim();

        if(username.isEmpty() || password.isEmpty() || email.isEmpty())
        {
            Toast.makeText(this, "Field are required", Toast.LENGTH_SHORT).show();
            return;
        }

        mUser.setLogin(username);
        mUser.setEmail(email);
        mUser.setPassword_hash(password);
        Log.e(TAG, "User check: "+mUser.toString().trim());

        createUser();

        //kiem tra da co user do chua
    }

    //   bam nut back thi ket thuc this, quay lai activity call this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();

    }





}