package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {
    private Button buttonLogin;
    private TextView daftar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        buttonLogin = findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener() {
        public void onClick (View v){
            // action
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
    });
        daftar = findViewById(R.id.daftar);
        daftar.setOnClickListener(new View.OnClickListener() {
            public void onClick (View v){
                // action
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
            }
        });

}
}