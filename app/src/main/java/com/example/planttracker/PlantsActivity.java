package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.databinding.ActivityPlantsBinding;
import com.example.planttracker.viewHolders.PlantAdapter;
import com.example.planttracker.viewHolders.PlantViewModel;

public class PlantsActivity extends AppCompatActivity {
    private ActivityPlantsBinding binding;
    private int loggedInUserID;
    private PlantViewModel plantViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // Set up Plant recycler view
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        RecyclerView recyclerView = binding.plantsActivityRecyclerView;
        final PlantAdapter adapter = new PlantAdapter(new PlantAdapter.GymLogDiff());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        plantViewModel.getAllPlantsByUserID(loggedInUserID).observe(this, adapter::submitList);

        binding.plantsActivityAddNewPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditPlantActivity.editPlantActivityIntentFactory(getApplicationContext(), true);
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