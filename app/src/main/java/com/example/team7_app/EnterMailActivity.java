package com.example.team7_app;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

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
//                Intent intent =  new Intent(EnterMailActivity.this, OTPActivity.class);
//                startActivity(intent);
                resetPassword(email.getText().toString().trim());
                finish();
            }
        });
    }

    private void resetPassword(String email) {
//        Toast.makeText(getApplicationContext(), email, Toast.LENGTH_SHORT).show();
        Log.i(TAG, "Email for reset pass is: " + email);
    }
    // bam nut back thi ket thuc this, quay lai activity call this
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}