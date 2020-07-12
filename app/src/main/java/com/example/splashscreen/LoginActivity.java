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

import com.example.splashscreen.apihelper.AppService;
import com.example.splashscreen.model.LoginBody;
import com.example.splashscreen.model.LoginResult;
import com.example.splashscreen.apihelper.UserApiService;
import com.example.splashscreen.apihelper.RetrofitUtility;
import com.example.splashscreen.apihelper.Utility;

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


        tUsername = findViewById(R.id.Username);
        tPassword = findViewById(R.id.Password);
        buttonLogin = findViewById(R.id.buttonLogin);
        daftar = findViewById(R.id.daftar);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tUsername.getText().toString().length() == 0) {
                    tUsername.setError("tidak boleh kosong");
                } else if (tPassword.getText().toString().length() == 0) {
                    tPassword.setError("tidak boleh kosong");
                } else {
                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                    loginSubmit(tUsername.getText().toString(), tPassword.getText().toString());
                }
            }
        });

        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // action
                Intent intent = new Intent(LoginActivity.this, signup.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void loginSubmit(String userName, String password) {
        LoginBody loginBody = new LoginBody(userName, password);
        Log.e("TAG", "loginSubmit: " + userName);
        Log.e("TAG", "loginSubmit: " + password);

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<LoginResult> result = apiService.getResultInfo(loginBody);

        result.enqueue(new Callback<LoginResult>() {
            @Override
            public void onResponse(Call<LoginResult> call, Response<LoginResult> response) {
                Toast.makeText(LoginActivity.this, "Logiin gagal", Toast.LENGTH_SHORT).show();
                try {
                    if (response.body().isSuccess()) {
                        Toast.makeText(LoginActivity.this, "Logiin berhasil", Toast.LENGTH_SHORT).show();
                        AppService.setToken("Bearer " + response.body().getToken());
                        Intent intent = new Intent(LoginActivity.this, BukuActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(LoginActivity.this, "Logiin gagal", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<LoginResult> call, Throwable t) {t.printStackTrace();
            }
        });

    }
}



