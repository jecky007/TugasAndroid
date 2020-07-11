package com.example.splashscreen.login;

import com.example.splashscreen.signupmodel.SignupBody;
import com.example.splashscreen.signupmodel.SignupResult;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserApiService {
    @POST("login")
    Call<LoginResult> getResultInfo(@Body LoginBody loginBody);

    @POST("api/user")
    Call<SignupResult> signupuser(@Body SignupBody body);
}
