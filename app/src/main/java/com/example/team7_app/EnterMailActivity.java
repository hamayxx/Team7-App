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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EnterMailActivity extends AppCompatActivity {
    private final static String TAG = "TEAM8_DEBUG";
    CardView cvToOPT;
    private EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_mail);
        //getSupportActionBar().hide();
        email = findViewById(R.id.a_enter_email_et_mail);

        cvToOPT= findViewById(R.id.a_enter_email_btn_login);
        cvToOPT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetPassword(email.getText().toString().trim());
                finish();
            }
        });
    }

    private void resetPassword(String email) {
//        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Email for reset pass is: " + email);

        Log.i("TEAM8", "Getting list users from server!!!");

        APIService resetPasswordService = ServiceGenerator.createService(APIService.class);
        Call<String> call = resetPasswordService.resetPassword(email);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    Log.i(TAG, "Respone " + response.toString());
                    Toast.makeText(getApplicationContext(), "SUCCESS!\nOpen link in your email for resetting password", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    // error response, no access to resource?
                    Log.e(TAG, "Register FAILED!!!"+ response.toString());
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // something went completely south (like no internet connection)
                Log.e(TAG, t.getMessage());
                Log.e(TAG, call.toString());
                Toast.makeText(getApplicationContext(), "Cannot connect to server", Toast.LENGTH_SHORT).show();
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