package com.example.cozart.lend.Views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cozart.lend.R;

public class MainActivity extends AppCompatActivity {

    Button mGoToSignUp;
    Button mButtonLogin;
    TextView mResetPW;

    EditText mEditTextMail;
    EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mButtonLogin = (Button) findViewById(R.id.login_button);
        mGoToSignUp = (Button) findViewById(R.id.go_signup);
        mResetPW = (TextView) findViewById(R.id.go_reset_pw);

        mEditTextMail = (EditText) findViewById(R.id.login_mail);
        mEditTextPassword = (EditText) findViewById(R.id.login_password);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditTextMail.getText().toString().isEmpty() || mEditTextPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Veillez remplir l'ensemble des champs", Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(MainActivity.this, "Yeeeees It WORKKK !!!!!!", Toast.LENGTH_LONG).show();

                }
            }
        });

        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(MainActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        mResetPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentResetPW = new Intent(MainActivity.this, ResetPWActivity.class);
                startActivity(intentResetPW);
            }
        });

    }
}
