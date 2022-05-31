package com.example.team7_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class NewPasswordActivity extends AppCompatActivity {
    CardView cvToLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        //getSupportActionBar().hide();

        cvToLogin=findViewById(R.id.a_new_password_btn_accept);
        cvToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NewPasswordActivity.this, LoginActivity.class);
                startActivity(intent);
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