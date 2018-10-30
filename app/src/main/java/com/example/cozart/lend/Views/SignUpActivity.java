package com.example.cozart.lend.Views;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.cozart.lend.R;
import com.example.cozart.lend.Views.Model.UserModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    private Calendar myCalendar = Calendar.getInstance();
    private EditText mEtBirthday;
    private Button mGoToLogin;



    private FirebaseAuth mAuth;
    private ProgressBar mProgressBar;
    private SharedPreferences mySharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

      mAuth = FirebaseAuth.getInstance();
      mySharedPref = getSharedPreferences("SP", MODE_PRIVATE);

      final EditText etLastname = findViewById(R.id.signup_last_name);
      final EditText etFirstname = findViewById(R.id.signup_first_name);
      final EditText etMail = findViewById(R.id.signup_mail);
      final EditText etPassword = findViewById(R.id.signup_password);
      final EditText etConfirmPassword = findViewById(R.id.signup_confirm_password);
      final EditText etPhone = findViewById(R.id.signup_phone);
      final EditText etCity = findViewById(R.id.signup_city);
      mProgressBar = findViewById(R.id.signup_progress_bar);
      mProgressBar.setVisibility(View.GONE);
      mEtBirthday = (EditText) findViewById(R.id.signup_birthday);
      mGoToLogin = (Button) findViewById(R.id.go_login);
      Button btnSignup = findViewById(R.id.signup_button);


      btnSignup.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              final String lastname = etLastname.getText().toString().trim();
              final String firstname = etFirstname.getText().toString().trim();
              final String email = etMail.getText().toString().trim();
              final String password = etPassword.getText().toString().trim();
              final String confirmPassword = etConfirmPassword.getText().toString().trim();
              final String birthday = mEtBirthday.getText().toString().trim();
              final String phone = etPhone.getText().toString().trim();
              final String city = etCity.getText().toString().trim();

              if (lastname.isEmpty()
                      || firstname.isEmpty()
                      || email.isEmpty()
                      || password.isEmpty()
                      || birthday.isEmpty()
                      || phone.isEmpty()
                      || city.isEmpty()){
                  Toast.makeText(SignUpActivity.this,
                          "Vous devez remplir tous les champs pour créer un compte !",
                          Toast.LENGTH_LONG).show();
              }else {
                  if (!password.equals(confirmPassword)) {
                      etPassword.setText("");
                      etConfirmPassword.setText("");
                      Toast.makeText(SignUpActivity.this, "Les deux mots de passe doivent etre similaire",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(email)) {
                      Toast.makeText(getApplicationContext(),"Veillez entrer votre addresse email",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(lastname)) {
                      Toast.makeText(getApplicationContext(), "Veuillez ajouter votre nom",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(firstname)) {
                      Toast.makeText(getApplicationContext(), "Veuillez ajouter votre prénom",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(birthday)) {
                      Toast.makeText(getApplicationContext(), "Veuillez ajouter date de naissance",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(city)) {
                      Toast.makeText(getApplicationContext(), "Veuillez ajouter votre ville",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(password)) {
                      Toast.makeText(getApplicationContext(),"Veillez entrer votre mot de passe",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (TextUtils.isEmpty(confirmPassword)) {
                      Toast.makeText(getApplicationContext(), "Veuillez confimer votre mot de passe",
                              Toast.LENGTH_LONG).show();
                      return;
                  }
                  if (password.length() < 6) {
                      etPassword.setText("");
                      etConfirmPassword.setText("");
                      Toast.makeText(SignUpActivity.this,"Votre mot de passse doit contenir minimum 6 caractère",
                              Toast.LENGTH_LONG).show();
                      return;
                  }  mProgressBar.setVisibility(View.VISIBLE);
                  mAuth.createUserWithEmailAndPassword(email, password)
                          .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                              @Override
                              public void onComplete(@NonNull Task<AuthResult> task) {

                                  mProgressBar.setVisibility(View.GONE);

                                  if (!task.isSuccessful()) {
                                      Toast.makeText(SignUpActivity.this,
                                              "Une erreur est survenue l'hors de la création de votre compte ou l'adresse email est déja utiliser",
                                              Toast.LENGTH_LONG).show();
                                  } else {
                                      Toast.makeText(SignUpActivity.this, "Compte créé avec succès",
                                              Toast.LENGTH_LONG).show();

                                      UserModel myUser = new UserModel(lastname, firstname, email, birthday, phone, city);
                                      signupUserInDatabase(myUser);
                                      signUserInSharedPref(myUser);
                                      // On va sur la page Ma Milos
                                      Intent intent = new Intent(SignUpActivity.this, TestActivity.class);
                                      startActivity(intent);
                                      finish();
                                  }
                              }
                          });

              }
          }
      });


        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }

        };

        mEtBirthday.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        mGoToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetSharedPreferences();
                Intent goToLogin = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(goToLogin);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //  mProgressBar.setVisibility(View.GONE);
    }

    public void onBackPressed() {
        Intent i = new Intent(SignUpActivity.this, MainActivity.class);
        startActivity(i);
    }

    private void resetSharedPreferences(){
        mySharedPref.edit().putBoolean("isAlreadyUser", false).apply();
        mySharedPref.edit().putString("lastName", "").apply();
        mySharedPref.edit().putString("firstName", "").apply();
        mySharedPref.edit().putString("email", "").apply();
        mySharedPref.edit().putString("birthday", "").apply();
        mySharedPref.edit().putString("phone", "").apply();
        mySharedPref.edit().putString("city", "").apply();
    }


    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);

        mEtBirthday.setText(sdf.format(myCalendar.getTime()));
    }

    public void signupUserInDatabase(UserModel myUser){
        //Initialize Firebase components
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference usersDatabaseReference = database.getReference().child("users");
        // On enregistre le user en DB et dans les SP
        usersDatabaseReference.push().setValue(myUser);
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
}
