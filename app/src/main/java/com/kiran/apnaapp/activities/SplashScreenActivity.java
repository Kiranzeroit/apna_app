package com.kiran.apnaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.kiran.apnaapp.MainActivity;
import com.kiran.apnaapp.Session;
import com.kiran.apnaapp.databinding.ActivitySplashScreenBinding;

public class SplashScreenActivity extends AppCompatActivity {
    private ActivitySplashScreenBinding binding;
    private boolean handler;
    private Session session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySplashScreenBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        initView();
    }

    private void initView() {
        handler = new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.getBooleanValue("Login")) {
                    Intent intent = new Intent(SplashScreenActivity.this, MainActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Intent intent = new Intent(SplashScreenActivity.this, SignInActivity.class);
                    startActivity(intent);
                    finishAffinity();
                }
            }
        }, 2000);
    }
}