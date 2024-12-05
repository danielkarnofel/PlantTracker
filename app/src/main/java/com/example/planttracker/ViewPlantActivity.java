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

        // TODO: implement onClick for Water Plant button

        // TODO: implement onClick for Edit button

        // TODO: implement onClick for Back button
    }

    // TODO: implement basic intent factory
}