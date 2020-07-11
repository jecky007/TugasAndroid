package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.splashscreen.login.LoginBody;
import com.example.splashscreen.login.LoginResult;
import com.example.splashscreen.login.UserApiService;
import com.example.splashscreen.utility.RetrofitUtility;
import com.example.splashscreen.utility.Utility;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class LoginActivity extends AppCompatActivity {
    private Retrofit retrofit;
    private Button buttonLogin;
    private TextView daftar;
    private EditText tUsername, tPassword;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Utility.askPermission(this);
        retrofit = RetrofitUtility.initializeRetrofit();
        init();
    }

    private void loginSubmit(String userName, String password) {
        LoginBody loginBody = new LoginBody(userName, password);
        Log.e("TAG", "loginSubmit: "+ userName);
        Log.e("TAG", "loginSubmit: " + password );

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<LoginResult> result = apiService.getResultInfo(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                try {
                    if (response.body().isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Logiin berhasil", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(LoginActivity.this, "Logiin gagal", Toast.LENGTH_SHORT).show();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {

            }
        });






//        tUsername = (EditText) findViewById(R.id.Username);
//        tPassword = (EditText) findViewById(R.id.Password);
//        buttonLogin = (Button) findViewById(R.id.buttonLogin);
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//
//
//            public void onClick(View v) {
//                // action
//                buttonLogin = findViewById(R.id.button);
//                buttonLogin.setOnClickListener(new View.OnClickListener() {
//                Intent intent = new Intent(LoginActivity.this, signup.class);
//                startActivity(intent);
//
//                String username = tUsername.getText().toString();
//                String password = tPassword.getText().toString();
//
//                if (username.equals("")) {
//                    tUsername.setError("username tidak boleh kosong");
//                } else if (password.equals("")) {
//                    Toast.makeText(LoginActivity.this, "password anda kosong", Toast.LENGTH_SHORT).show();
//                }
//                {
//                    Log.v("login", "berhasil");
//                }
//            }
//
//        });


    }

    private void init (){
        tUsername = findViewById(R.id.Username);
        tPassword = findViewById(R.id.Password);
        buttonLogin = findViewById(R.id.buttonLogin);
        daftar = findViewById(R.id.daftar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginSubmit(tUsername.getText().toString(),tPassword.getText().toString());

            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // action
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
            }
        });
    }
}


