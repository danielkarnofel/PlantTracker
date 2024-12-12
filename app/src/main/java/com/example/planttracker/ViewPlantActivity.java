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
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityViewPlantBinding;
import com.example.planttracker.utilities.IntentFactory;

import java.time.LocalDateTime;

public class ViewPlantActivity extends BaseActivity {
    private ActivityViewPlantBinding binding;
    private int selectedPlantID;
    public Plant selectedPlant;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewPlantBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        LiveData<Plant> plantObserver = repository.getPlantByID(selectedPlantID);
        plantObserver.observe(this, plant -> {
            this.selectedPlant = plant;
            if (plant != null) {
                invalidateOptionsMenu();
            }
        });

        // TODO: implement onClick for Water Plant button
        binding.viewPlantActivityWaterPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedPlant.setLastWatered(LocalDateTime.now());
            }
        });

        // implement onClick for Edit button
        binding.viewPlantActivityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.editPlantActivityIntentFactory(getApplicationContext(), selectedPlantID));
            }
        });

        // implement onClick for Back button
        binding.viewPlantActivityBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.plantsActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    // implement basic intent factory

}