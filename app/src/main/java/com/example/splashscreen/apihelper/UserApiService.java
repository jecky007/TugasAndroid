package com.example.splashscreen.apihelper;

import com.example.splashscreen.model.LoginBody;
import com.example.splashscreen.model.LoginResult;
import com.example.splashscreen.model.SignupBody;
import com.example.splashscreen.model.SignupResult;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignupResult> signupuser(@Body SignupBody body);
}
