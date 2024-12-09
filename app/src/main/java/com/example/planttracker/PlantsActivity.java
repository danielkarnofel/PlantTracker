package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityPlantsBinding;

public class PlantsActivity extends AppCompatActivity {
    private ActivityPlantsBinding binding;
    private int loggedInUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // TODO: Set up recycler view

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
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), MainActivity.getLoggedInUserID());
                startActivity(intent);
            }
        });
    }

    public static Intent plantsActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, PlantsActivity.class);
    }
}