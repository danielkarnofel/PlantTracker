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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityPlantsBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.viewHolders.PlantAdapter;
import com.example.planttracker.viewHolders.PlantViewModel;

public class PlantsActivity extends BaseActivity {
    private ActivityPlantsBinding binding;
    private PlantViewModel plantViewModel;
    protected static final int NEW_PLANT_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up Plant recycler view
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        RecyclerView recyclerView = binding.plantsActivityRecyclerView;
        final PlantAdapter adapter = new PlantAdapter(new PlantAdapter.PlantDiff());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        plantViewModel.getAllPlantsByUserID(loggedInUserID).observe(this, adapter::submitList);

        binding.plantsActivityAddNewPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.editPlantActivityIntentFactory(getApplicationContext(), NEW_PLANT_ID);
                startActivity(intent);
            }
        });

        binding.plantsActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.mainActivityIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });
    }


}