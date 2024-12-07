package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityPlantsBinding;

public class PlantsActivity extends AppCompatActivity {
    private ActivityPlantsBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.plantsActivityAddNewPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditPlantActivity.editPlantActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.plantsActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    public static Intent plantsActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, PlantsActivity.class);
    }
}