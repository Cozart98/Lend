package com.example.cozart.lend.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.cozart.lend.R;

public class ResetPWActivity extends AppCompatActivity {

    Button mGoToSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        mGoToSignUp = (Button) findViewById(R.id.go_signup);

        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(ResetPWActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });
    }
}
