package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.planttracker.databinding.ActivityEditPlantBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.LightLevel;

public class EditPlantActivity extends BaseActivity {
    private ActivityEditPlantBinding binding;
    private int selectedPlantID;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // get selectedPlantID from extra

        // if -1, selectedPlant = new Plant
        // else, get selectedPlant from database with getPlantByID




        // TODO: implement onClick for Save button
        binding.editPlantActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: implement functionality

                // TODO: Use this to get the light level from the selected radio button, returns null if nothing selected yet
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editPlantActivityLightLevelNeededRadioGroup);

                // following functionality, the button will return the user to ViewPlantActivity
                startActivity(IntentFactory.viewPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        // TODO: implement onClick for Cancel button
        binding.editPlantActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: if isNewPlant is true, the EditPlantActivity will need to delete the new plant from the database when the user presses cancel
                // following functionality, the button will return the user to ViewPlantActivity
                startActivity(IntentFactory.viewPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        // onClick for Delete button
        binding.editPlantActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.plantsActivityIntentFactory(getApplicationContext()));
            }
        });;
    }

    // TODO: implement basic intent factory

}