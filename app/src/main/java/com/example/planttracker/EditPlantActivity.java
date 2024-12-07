package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityEditPlantBinding;

public class EditPlantActivity extends AppCompatActivity {
    private ActivityEditPlantBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: implement onClick for Save button

        // TODO: implement onClick for Cancel button

        // TODO: implement onClick for Delete button
    }

    // TODO: implement basic intent factory
    public static Intent editPlantActivityIntentFactory(Context applicationContext) {
        return null; // placeholder just to make code compile
    }
}