package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityPlantsBinding;

public class PlantsActivity extends AppCompatActivity {
    private ActivityPlantsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: implement onClick for Add New Plant button

        // TODO: implement onClick for Home Button
    }

    // TODO: implement basic intent factory
    public static Intent PlantsActivityIntentFactory(Context applicationContext) {
        return null;
    }
}