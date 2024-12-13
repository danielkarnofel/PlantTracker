package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityViewPlantBinding;
import com.example.planttracker.utilities.IntentFactory;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewPlantActivity extends BaseActivity {
    private ActivityViewPlantBinding binding;
    private int selectedPlantID;
    public Plant selectedPlant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedPlantID = getIntent().getIntExtra(IntentFactory.VIEW_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, PlantsActivity.NEW_PLANT_ID);

        LiveData<Plant> plantObserver = repository.getPlantByPlantID(selectedPlantID);
        plantObserver.observe(this, plant -> {
            this.selectedPlant = plant;
            if (plant != null) {
                updateDisplay();
            }
        });

        binding.viewPlantActivityWaterPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LiveData<Plant> plantObserver = repository.getPlantByPlantID(selectedPlantID);
                plantObserver.observe(ViewPlantActivity.this, plant -> {
                    selectedPlant = plant;
                    if (plant != null) {
                        selectedPlant.setLastWatered(LocalDateTime.now());
                        repository.updatePlant(selectedPlant);
                    }
                });
            }
        });

        binding.viewPlantActivityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.editPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        binding.viewPlantActivityBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.plantsActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    private void updateDisplay() {
        binding.viewPlantActivityNameTextView.setText(selectedPlant.getName());
        binding.viewPlantActivityTypeTextView.setText(selectedPlant.getType());
        binding.viewPlantActivityLightLevelNeededTextView.setText(selectedPlant.getLightLevelNeeded().toString());
        binding.viewPlantActivityWateringFrequencyTextView.setText(String.valueOf(selectedPlant.getWateringFrequency()));
        String lastWateredFormatted = selectedPlant.getLastWatered().format(DateTimeFormatter.ofPattern("MM-dd HH:mm"));
        binding.viewPlantActivityLastWateredTextView.setText(lastWateredFormatted);

        LiveData<Area> areaObserver = repository.getAreaByAreaID(selectedPlant.getAreaID());
        areaObserver.observe(this, area -> {
            if (area != null) {
                binding.viewPlantActivityAreaTextView.setText(area.getName());
            }
        });
    }
}