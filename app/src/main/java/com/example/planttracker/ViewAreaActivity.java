package com.example.planttracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityViewAreaBinding;

public class ViewAreaActivity extends AppCompatActivity {
    private ActivityViewAreaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}