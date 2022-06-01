package com.example.team7_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.team7_app.API.APIService;
import com.example.team7_app.API.ServiceGenerator;
import com.example.team7_app.Model.RegisterUserDTO;
import com.example.team7_app.Model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {
    CardView cvToLogin;

    private EditText etUsername;
    private EditText etPassword;
    private EditText etEmail;
    private EditText etRePassword;
    private User mUser ;
    private final static String TAG = "TEAM8_DEBUGGER";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //getSupportActionBar().hide();

        etUsername=findViewById(R.id.a_register_et_user);
        etPassword=findViewById(R.id.a_register_et_password);
        etEmail=findViewById(R.id.a_register_et_mail);
        etRePassword=findViewById(R.id.a_register_et_repass);



        cvToLogin = findViewById(R.id.a_register_btn_register);
        cvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUp();
            }
        });

    }

    private void createUser(RegisterUserDTO registerUserDTO) {
        Log.i("TEAM8", "Getting list users from server!!!");
        APIService signupService = ServiceGenerator.createService(APIService.class);
        Call<RegisterUserDTO> call = signupService.createUser(registerUserDTO);
        Log.e(TAG, "User API: "+ registerUserDTO.toString().trim());

        call.enqueue(new Callback<RegisterUserDTO>() {
            @Override
            public void onResponse(Call<RegisterUserDTO> call, Response<RegisterUserDTO> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Respone" + response.toString());
//                    Toast.makeText(RegisterActivity.this, "Call API <post> success", Toast.LENGTH_SHORT).show();
//
//                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
//                    startActivity(intent);
                } else {
                    // error response, no access to resource?
                    Log.e(TAG, "Register FAILED!!!"+ response.toString());
                }

            }

            @Override
            public void onFailure(Call<RegisterUserDTO> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());

                Toast.makeText(RegisterActivity.this, "Register successfully!!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void onSignUp() {
        //kiem tra dau vao
        String username = etUsername.getText().toString().trim();
        String password= etPassword.getText().toString().trim();
        String email= etEmail.getText().toString().trim();
        String repass= etRePassword.getText().toString().trim();


        if(username.isEmpty() || password.isEmpty() || email.isEmpty() || (!password.equals(repass)))
        {
            Toast.makeText(this, "Field are required", Toast.LENGTH_SHORT).show();
            return;
        }

        RegisterUserDTO registerUserDTO = new RegisterUserDTO(username, email, password, "en");
        Log.e(TAG, "User check: "+ registerUserDTO.toString().trim());

        createUser(registerUserDTO);

        //kiem tra da co user do chua
    }

    //   bam nut back thi ket thuc this, quay lai activity call this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}