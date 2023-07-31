package com.kiran.apnaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiran.apnaapp.CommonUtilities;
import com.kiran.apnaapp.MainActivity;
import com.kiran.apnaapp.Session;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.databinding.ActivitySignInBinding;

public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignInBinding binding;
    private DatabaseHelper databaseHelper;
    private Session session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignInBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        session = new Session(this);
        databaseHelper = new DatabaseHelper(this);
        initView();
    }

    private void initView() {
        binding.btnSignIn.setOnClickListener(this);
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignIn) {
            if (isValidation()) {
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                boolean isUserExist = databaseHelper.isUserExist(email, password);
                if (isUserExist) {
                    session.setBooleanValue("Login", true);
                    session.setStringValue("email", email);
                    Toast.makeText(this, "Sign in Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignInActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(this, "user doesn't exist, please sign up", Toast.LENGTH_SHORT).show();
                }
            }
        } else if (view == binding.btnSignUp) {
            Intent intent = new Intent(SignInActivity.this, SignUpActivity.class);
            startActivity(intent);
        }
    }

    private boolean isValidation() {
        if (binding.etEmail.getText().toString().trim().isEmpty()) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("First enter your email address");
            return false;
        } else if (!CommonUtilities.isValidEmail(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("First re-check your email address");
            return false;
        } else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("First enter your password");
            return false;
        } else if (!CommonUtilities.isValidPassword(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("Password must have at-least 6 digits");
            return false;
        }
        return true;
    }
}
