package com.example.cozart.lend.Views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cozart.lend.R;
import com.example.cozart.lend.Views.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mUsersDatabaseReference;
    private FirebaseAuth mAuth;
    private UserModel myUser;
    private ProgressDialog mProgDial;
    private List<UserModel> mUsertab = new ArrayList<>();
    private SharedPreferences mySharedPref;

    Button mGoToSignUp;
    Button mButtonLogin;

    TextView mResetPW;

    EditText mEditTextMail;
    EditText mEditTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mySharedPref = getSharedPreferences("SP", MODE_PRIVATE);

        mButtonLogin = (Button) findViewById(R.id.login_button);
        mGoToSignUp = (Button) findViewById(R.id.go_signup);
        mResetPW = (TextView) findViewById(R.id.go_reset_pw);

        mEditTextMail = (EditText) findViewById(R.id.login_mail);
        mEditTextPassword = (EditText) findViewById(R.id.login_password);

        mButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEditTextMail.getText().toString().trim();
                final String password = mEditTextPassword.getText().toString().trim();

                if (mEditTextMail.getText().toString().isEmpty() || mEditTextPassword.getText().toString().isEmpty()){
                    Toast.makeText(MainActivity.this, "Veillez remplir l'ensemble des champs", Toast.LENGTH_LONG).show();
                }else {
                    mProgDial = new ProgressDialog(MainActivity.this);
                    mProgDial.setIndeterminate(false);
                    mProgDial.setCancelable(false);
                    mProgDial.setMessage("veuillez patientez");
                    mProgDial.show();

                    mDatabase = FirebaseDatabase.getInstance();
                    mUsersDatabaseReference = mDatabase.getReference().child("users/");

                    mUsersDatabaseReference.addValueEventListener(
                            new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (mUsertab.size() > 0) {
                                        mUsertab.clear();
                                    }
                                    for (DataSnapshot sp : dataSnapshot.getChildren()) {
                                        myUser = sp.getValue(UserModel.class);
                                        mUsertab.add(myUser);
                                        if (myUser.getMail().equals(email)) {
                                            signInPgm(email, password);
                                            mProgDial.dismiss();
                                            return;
                                        }
                                    }
                                    if (!myUser.getMail().equals(email)) {
                                        mProgDial.dismiss();
                                        Toast.makeText(MainActivity.this,
                                                "auth incorrect", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    Toast.makeText(MainActivity.this, "utilisateur inconnu",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });

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
                Intent intentResetPW = new Intent(MainActivity.this, ResetPasswordActivity.class);
                startActivity(intentResetPW);
            }
        });

    }

    public void signUserInSharedPref(UserModel myUser){
        mySharedPref.edit().putBoolean("isAlreadyUser", true).apply();
        mySharedPref.edit().putString("lastName", myUser.getLastname()).apply();
        mySharedPref.edit().putString("firstName", myUser.getFirstname()).apply();
        mySharedPref.edit().putString("email", myUser.getMail()).apply();
        mySharedPref.edit().putString("birthday", myUser.getBirthday()).apply();
        mySharedPref.edit().putString("phone", myUser.getPhone()).apply();
        mySharedPref.edit().putString("city", myUser.getCity()).apply();
    }

    public void signInPgm(String email,String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(MainActivity.this,
                        new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {


                                if (!task.isSuccessful()) {
                                    mProgDial.dismiss();
                                    Toast.makeText(MainActivity.this, "Mauvais mot de pass", Toast.LENGTH_LONG).show();
                                } else {
                                    mProgDial.dismiss();
                                    signUserInSharedPref(myUser);
                                    Intent intent = new Intent(MainActivity.this,
                                            TestActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });
    }
}
