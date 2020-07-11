package com.example.splashscreen.utility;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;



public class Utility {
    private String TAG = "utility";

    public static void askPermission(Activity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.INTERNET},1);
    }

    public static boolean checkPermission(Context context){
        PackageManager pm = context.getPackageManager();
        int hasPerm = pm.checkPermission(Manifest.permission.INTERNET,context.getPackageName());

        boolean internetPermission = hasPerm == PackageManager.PERMISSION_GRANTED ? true : false;

        if (internetPermission) {
            return true;
        }else{
            return false;
        }
    }
}
