package com.example.planttracker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityViewPlantBinding;

public class ViewPlantActivity extends AppCompatActivity {
    private ActivityViewPlantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}