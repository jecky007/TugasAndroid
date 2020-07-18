package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.splashscreen.apiinterface.UserApiService;
import com.example.splashscreen.model.SignupBody;
import com.example.splashscreen.model.SignupResult;
import com.example.splashscreen.utility.RetrofitUtility;
import com.example.splashscreen.utility.Utility;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class signup extends AppCompatActivity {
    private Retrofit retrofit;
    private AwesomeValidation awesomeValidation;

    EditText username, Email, password, name;
    Spinner listitem;
    RadioButton radioButton, radioButton2;
    Button button;
    private boolean rbaktif;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        Utility.askPermission(this);
        retrofit = RetrofitUtility.initializeRetrofit();

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        Email = findViewById(R.id.Email);
        name = findViewById(R.id.name);
        listitem = findViewById(R.id.listitem);
        radioButton = findViewById(R.id.radioButton);
        radioButton2 = findViewById(R.id.radioButton2);
        button = findViewById(R.id.button);

        awesomeValidation.addValidation(this, R.id.username, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.usernameerror);
        awesomeValidation.addValidation(this, R.id.Email, Patterns.EMAIL_ADDRESS, R.string.emailerror);
        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.passwordeerror);
        awesomeValidation.addValidation(this, R.id.name, "^[A-Za-z\\s]{1,}[\\.]{0,1}[A-Za-z\\s]{0,}$", R.string.nameerror);
        //awesomeValidation.addValidation(this, R.id.name, "^(?:(?:31(\\/|-|\\.)(?:0?[13578]|1[02]))\\1|(?:(?:29|30)(\\/|-|\\.)(?:0?[1,3-9]|1[0-2])\\2))(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$|^(?:29(\\/|-|\\.)0?2\\3(?:(?:(?:1[6-9]|[2-9]\\d)?(?:0[48]|[2468][048]|[13579][26])|(?:(?:16|[2468][048]|[3579][26])00))))$|^(?:0?[1-9]|1\\d|2[0-8])(\\/|-|\\.)(?:(?:0?[1-9])|(?:1[0-2]))\\4(?:(?:1[6-9]|[2-9]\\d)?\\d{2})$", R.string.nameerror);
       // awesomeValidation.addValidation(this, R.id.editTextAge, Range.closed(13, 60), R.string.ageerror);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if (awesomeValidation.validate()){
                   List<String>roles1 = new ArrayList<>();
                   roles1.add(listitem.getSelectedItem().toString());
                   signupSubmit(username.getText().toString(), password.getText().toString(), Email.getText().toString(), name.getText().toString(),roles1, rbaktif );

               }else{
                   Toast.makeText(getApplicationContext(),"Signup Gagal", Toast.LENGTH_SHORT);
               }
            }
        });


    }
    private void signupSubmit(String username, String password, String Email, String name, List<String> roles, boolean active) {
        SignupBody signupBody = new SignupBody(username, password, Email, name,roles, active);

        UserApiService apiService = retrofit.create(UserApiService.class);
        Call<SignupResult> result = apiService.signupuser(signupBody);

        result.enqueue(new Callback<SignupResult>() {
            @Override
            public void onResponse(Call<SignupResult> call, Response<SignupResult> response) {
                boolean success = response.body().isSuccess();
                try {
                    if (response.body().isSuccess()) {
                        Log.e("TAG", "Signup Success " + response.body().toString());
                        Intent LoginIntent = new Intent(signup.this, LoginActivity.class);
                        startActivity(LoginIntent);
                        finish();
                    } else {
                        Toast.makeText(signup.this,  "Signup Gagal :" + response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<SignupResult> call, Throwable t) {

            }
        });

    }

}