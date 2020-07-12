package com.example.splashscreen.apihelper;

public class AppService {
    private static String token;

    public AppService() {
    }

    public static String getToken() {
        return token;
    }

    public static void setToken(String token) {
        AppService.token = token;
    }
}
