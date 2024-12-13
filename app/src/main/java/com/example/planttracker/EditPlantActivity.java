package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.databinding.ActivityEditPlantBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.LightLevel;

import java.time.LocalDateTime;

public class EditPlantActivity extends BaseActivity {
    private ActivityEditPlantBinding binding;
    private int selectedPlantID;
    private Plant selectedPlant;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedPlantID = getIntent().getIntExtra(IntentFactory.EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, PlantsActivity.NEW_PLANT_ID);

        if (selectedPlantID != PlantsActivity.NEW_PLANT_ID) {
            autoFillValues();
        }

        binding.editPlantActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int areaID = 1; // TODO: User should be able to select from existing areas, or select a default area
                String name = binding.editPlantActivityNameEditText.getText().toString();
                String type = binding.editPlantActivityPlantTypeEditText.getText().toString();
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editPlantActivityLightLevelNeededRadioGroup);
                int wateringFrequency = 7; // TODO: User should be able to input the watering frequency

                if (selectedPlantID == PlantsActivity.NEW_PLANT_ID) {
                    repository.insertPlant(new Plant(loggedInUserID, areaID, name, type, selectedLightLevel, wateringFrequency, LocalDateTime.now()));
                } else {
                    LiveData<Plant> plantObserver = repository.getPlantByPlantID(selectedPlantID);
                    plantObserver.observe(EditPlantActivity.this, area -> {
                        selectedPlant = area;
                        if (selectedPlant != null) {
                            selectedPlant.setAreaID(areaID);
                            selectedPlant.setName(name);
                            selectedPlant.setType(type);
                            selectedPlant.setLightLevelNeeded(selectedLightLevel);
                            selectedPlant.setWateringFrequency(wateringFrequency);
                            repository.updatePlant(selectedPlant);
                        }
                    });
                }
                startActivity(IntentFactory.viewPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        binding.editPlantActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.viewPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        binding.editPlantActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPlantID != PlantsActivity.NEW_PLANT_ID) {
                    LiveData<Plant> plantObserver = repository.getPlantByPlantID(selectedPlantID);
                    plantObserver.observe(EditPlantActivity.this, plant -> {
                        selectedPlant = plant;
                        if (selectedPlant != null) {
                            repository.deletePlant(selectedPlant);
                        }
                    });
                }
                startActivity(IntentFactory.plantsActivityIntentFactory(getApplicationContext()));
            }
        });;
    }

    private void autoFillValues() {

    }
}