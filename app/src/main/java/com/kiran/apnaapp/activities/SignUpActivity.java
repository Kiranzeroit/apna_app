package com.kiran.apnaapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kiran.apnaapp.CommonUtilities;
import com.kiran.apnaapp.database.DatabaseHelper;
import com.kiran.apnaapp.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivitySignUpBinding binding;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
        databaseHelper = new DatabaseHelper(this);

    }

    private void initView() {
        binding.btnSignUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view == binding.btnSignUp) {
            if (isValidation()) {
                String name = binding.etName.getText().toString().trim();
                String mobile = binding.etMobile.getText().toString().trim();
                String city = binding.etCity.getText().toString().trim();
                String email = binding.etEmail.getText().toString().trim();
                String password = binding.etPassword.getText().toString().trim();
                String confirmPassword = binding.etConfirmPassword.getText().toString().trim();


                boolean isUserExist = databaseHelper.isUserExist(email, password);

                if (!isUserExist) {
                    databaseHelper.saveUserDetails(name, mobile, city, email, password, confirmPassword);
                    Toast.makeText(this, "Sign up successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, SignInActivity.class);
                    startActivity(intent);
                    finishAffinity();
                } else {
                    Toast.makeText(this, "user is already exist, please sign in", Toast.LENGTH_SHORT).show();
                }
            }

        }
    }

    private boolean isValidation() {
        if (binding.etName.getText().toString().trim().isEmpty()) {
            binding.etName.requestFocus();
            binding.etName.setError("First enter your name");
            return false;
        } else if (binding.etMobile.getText().toString().trim().isEmpty()) {
            binding.etMobile.requestFocus();
            binding.etMobile.setError("First enter your mobile number with country code");
            return false;
        } else if (binding.etMobile.getText().toString().trim().length()!= 12) {
            binding.etMobile.requestFocus();
            binding.etMobile.setError("Re-check your mobile number");
            return false;
        } else if (binding.etCity.getText().toString().trim().isEmpty()) {
            binding.etCity.requestFocus();
            binding.etCity.setError("First enter your city");
            return false;
        } else if (binding.etEmail.getText().toString().trim().isEmpty()) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("First enter your email address");
            return false;
        } else if (!CommonUtilities.isValidEmail(binding.etEmail.getText().toString().trim())) {
            binding.etEmail.requestFocus();
            binding.etEmail.setError("Re-check your email address");
            return false;
        } else if (binding.etPassword.getText().toString().trim().isEmpty()) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("First enter your password");
            return false;
        } else if (!CommonUtilities.isValidPassword(binding.etPassword.getText().toString().trim())) {
            binding.etPassword.requestFocus();
            binding.etPassword.setError("Password must have at-least 8 digits");
            return false;
        } else if (binding.etConfirmPassword.getText().toString().trim().isEmpty()) {
            binding.etConfirmPassword.requestFocus();
            binding.etConfirmPassword.setError("First enter your password to confirm");
            return false;
        } else if (!binding.etPassword.getText().toString().trim().equalsIgnoreCase(binding.etConfirmPassword.getText().toString().trim())) {
            binding.etConfirmPassword.requestFocus();
            binding.etConfirmPassword.setError("Re-check your password");
            return false;
        }
        return true;
    }
}
