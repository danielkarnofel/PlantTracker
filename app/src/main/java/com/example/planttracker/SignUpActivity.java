package com.example.planttracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: implement onClick for Sign Up button

        // TODO: implement onClick for Cancel button
    }

    // TODO: implement intent factory
}