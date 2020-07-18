package com.example.splashscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.splashscreen.R;
import com.example.splashscreen.fragment.BukuFragment;
import com.example.splashscreen.fragment.DialogFragment;
import com.example.splashscreen.fragment.SecondFragment;

public class BukuActivity extends AppCompatActivity {

    Button button1;
    Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homefragment);
        openHomeFragment();

        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openHomeFragment();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondFragment();
            }
        });

    }
    public void openHomeFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        BukuFragment strCode = new BukuFragment();
        fragmentTransaction.replace(R.id.content, strCode,"home fragment");
        fragmentTransaction.commit();
    }

    public void openSecondFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        SecondFragment strCode = new SecondFragment();
        fragmentTransaction.replace(R.id.content, strCode,"second fragment");
        fragmentTransaction.commit();
    }

    public void openDialogFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        DialogFragment strCode = new DialogFragment();
        fragmentTransaction.replace(R.id.content, strCode,"second fragment");
        fragmentTransaction.commit();
    }
}