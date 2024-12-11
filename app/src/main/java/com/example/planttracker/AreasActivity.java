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

import com.example.planttracker.databinding.ActivityAreasBinding;
import com.example.planttracker.viewHolders.AreaAdapter;
import com.example.planttracker.viewHolders.AreaViewModel;
import com.example.planttracker.viewHolders.PlantAdapter;
import com.example.planttracker.viewHolders.PlantViewModel;

public class AreasActivity extends AppCompatActivity {
    private ActivityAreasBinding binding;
    private int loggedInUserID;
    private AreaViewModel areaViewModel;
    private static final int NEW_AREA_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAreasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // Set up Area recycler view
        areaViewModel = new ViewModelProvider(this).get(AreaViewModel.class);
        RecyclerView recyclerView = binding.areasActivityRecyclerView;
        final AreaAdapter adapter = new AreaAdapter(new AreaAdapter.AreaDiff());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        areaViewModel.getAllAreasByUserID(loggedInUserID).observe(this, adapter::submitList);

        binding.areasActivityAddNewAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditAreaActivity.editAreaActivityIntentFactory(getApplicationContext(), NEW_AREA_ID);
                startActivity(intent);
            }
        });

        binding.areasActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), MainActivity.getLoggedInUserID());
                startActivity(intent);
            }
        });
    }

    public static Intent areasActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, AreasActivity.class);
    }
}