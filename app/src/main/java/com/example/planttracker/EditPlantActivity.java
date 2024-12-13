package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.databinding.ActivityEditPlantBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.LightLevel;
import com.example.planttracker.utilities.ToastMaker;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class EditPlantActivity extends BaseActivity {
    private ActivityEditPlantBinding binding;
    private int selectedPlantID;
    private Plant selectedPlant;
    private int selectedAreaID;
    private static final int NO_AREA_SELECTED = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedPlantID = getIntent().getIntExtra(IntentFactory.EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, PlantsActivity.NEW_PLANT_ID);

        if (selectedPlantID != PlantsActivity.NEW_PLANT_ID) {
            autoFillValues();
        }

        LiveData<List<Area>> userAreasObserver = repository.getAllAreasByUserID(loggedInUserID);
        userAreasObserver.observe(this, userAreas -> {
            if (userAreas != null) {
                userAreas.add(new Area(loggedInUserID, "No Area", 0, LightLevel.MEDIUM)); // TODO: Figure out a proper way to add a default area for user's that don't have any
                ArrayAdapter<Area> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, userAreas);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                binding.editPlantActivityAreaSpinner.setAdapter(adapter);
            }
        });

        binding.editPlantActivityAreaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Object selected = parent.getItemAtPosition(position);
                if (selected instanceof Area) {
                    selectedAreaID = ((Area) selected).getAreaID();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedAreaID = NO_AREA_SELECTED;
            }
        });

        binding.editPlantActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int areaID = selectedAreaID;
                String name = binding.editPlantActivityNameEditText.getText().toString();
                String type = binding.editPlantActivityPlantTypeEditText.getText().toString();
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editPlantActivityLightLevelNeededRadioGroup);
                int wateringFrequency = -1;
                wateringFrequency = Integer.parseInt(binding.editPlantActivityWateringFrequencyEditText.getText().toString());

                if(plantInfoComplete(name,type,wateringFrequency)){
                    if (selectedPlantID == PlantsActivity.NEW_PLANT_ID) {
                        repository.insertPlant(new Plant(loggedInUserID, areaID, name, type, selectedLightLevel, wateringFrequency, LocalDateTime.now()));
                    } else {
                        LiveData<Plant> plantObserver = repository.getPlantByPlantID(selectedPlantID);
                        int finalWateringFrequency = wateringFrequency;
                        plantObserver.observe(EditPlantActivity.this, area -> {
                            selectedPlant = area;
                            if (selectedPlant != null) {
                                selectedPlant.setAreaID(areaID);
                                selectedPlant.setName(name);
                                selectedPlant.setType(type);
                                selectedPlant.setLightLevelNeeded(selectedLightLevel);
                                selectedPlant.setWateringFrequency(finalWateringFrequency);
                                repository.updatePlant(selectedPlant);
                            }
                        });
                        startActivity(IntentFactory.viewPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
                    }

                }

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

    private boolean plantInfoComplete(String name, String type, int waterFreq){
        if(name.isEmpty()){
            ToastMaker.makeToast(this, "Plant Name missing!");
            return false;
        }
        if(type.isEmpty()){
            ToastMaker.makeToast(this,"Plant Type missing!");
            return false;
        }
        if(waterFreq == -1){
            ToastMaker.makeToast(this,"Watering Frequency missing!");
            return false;
        }
        else{
            return true;
        }
    }
    private void autoFillValues() {

    }
}