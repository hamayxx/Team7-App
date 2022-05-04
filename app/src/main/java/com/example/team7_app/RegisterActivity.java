package com.example.team7_app;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.team7_app.Model.User;

public class RegisterActivity extends AppCompatActivity {
    CardView cvToLogin;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private User mUser;


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
//
//        APIService.apiService.createUser(mUser)
//                .enqueue(new Callback<User>() {
//                    @Override
//                    public void onResponse(Call<User> call, Response<User> response) {
//                        User usrResult = (User) response.body();
//                        if(usrResult!= null)
//                        {
//                            Log.e("Success: ", usrResult.toString()+" ");
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<User> call, Throwable t) {
//                        Toast.makeText(RegisterActivity.this, "Call API<post> error", Toast.LENGTH_SHORT).show();
//                        Log.e("Error API: ", t.toString()+" ");
//                    }
//                });
//
//
//
//        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//        startActivity(intent);
    }

    private void onSignUp() {
        //kiem tra dau vao
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
        mUser.setPassword(password);

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