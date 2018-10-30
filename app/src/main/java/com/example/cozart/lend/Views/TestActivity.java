package com.example.cozart.lend.Views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.cozart.lend.R;

public class TestActivity extends AppCompatActivity {

    private SharedPreferences mySharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        mySharedPref = getSharedPreferences("SP", MODE_PRIVATE);
        boolean isAlreadyUser = mySharedPref.getBoolean("isAlreadyUser", false);
        String lastName = mySharedPref.getString("lastName", "");
        String firstName = mySharedPref.getString("firstName", "");
        String email = mySharedPref.getString("email", "");
        String city = mySharedPref.getString("city", "");

        if (!isAlreadyUser) {
            Intent i = new Intent(TestActivity.this, SignUpActivity.class);
            startActivity(i);
        } else {
            // All the activity code must be here
            // Layout variables
            TextView tvHello = findViewById(R.id.textView2);
            TextView tvIamnot = findViewById(R.id.textView3);

            tvHello.setText(lastName);
            tvIamnot.setText(firstName + "  " + email + city);


        }
    }

    public void onBackPressed() {
        Intent i = new Intent(TestActivity.this, MainActivity.class);
        startActivity(i);
    }
}
