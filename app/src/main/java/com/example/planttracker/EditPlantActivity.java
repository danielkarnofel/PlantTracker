package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityEditPlantBinding;

public class EditPlantActivity extends AppCompatActivity {
    private ActivityEditPlantBinding binding;
    private int loggedInUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // TODO: implement onClick for Save button
        binding.editPlantActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: implement functionality
                // following functionality, the button will return the user to ViewPlantActivity
                startActivity(ViewPlantActivity.viewPlantActivityIntentFactory(getApplicationContext()));
            }
        });

        // TODO: implement onClick for Cancel button
        binding.editPlantActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: if isNewPlant is true, the EditPlantActivity will need to delete the new plant from the database when the user presses cancel
                // following functionality, the button will return the user to ViewPlantActivity
                startActivity(ViewPlantActivity.viewPlantActivityIntentFactory(getApplicationContext()));
            }
        });

        // onClick for Delete button
        binding.editPlantActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PlantsActivity.plantsActivityIntentFactory(getApplicationContext()));
            }
        });;
    }

    // TODO: implement basic intent factory
    public static Intent editPlantActivityIntentFactory(Context applicationContext, boolean isNewPlant) {
        // TODO: if isNewPlant is true, the EditPlantActivity will need to create a new plant when the user presses save

        return null; // placeholder just to make code compile
    }
}