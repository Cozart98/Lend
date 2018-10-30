package com.example.cozart.lend.Views;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.cozart.lend.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ResetPasswordActivity extends AppCompatActivity {

    Button mGoToSignUp;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_pw);

        mAuth = FirebaseAuth.getInstance();

        final EditText resetEmail = findViewById(R.id.et_reset_mail);
        Button resetPassword = findViewById(R.id.btn_reset_password);
        mGoToSignUp = (Button) findViewById(R.id.go_signup);

        mGoToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(ResetPasswordActivity.this, SignUpActivity.class);
                startActivity(intentSignUp);
            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = resetEmail.getText().toString().trim();

                if (email.equals("")) {
                    Toast.makeText(getApplication(), "Veuillez enter votre adresse mail",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(ResetPasswordActivity.this,
                                           "un mail de réinitialisation vous est envoyé",
                                            Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(ResetPasswordActivity.this,
                                            "Une erreur est survenu", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
            }
        });
    }
}
