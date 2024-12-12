package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.databinding.ActivityAreasBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.viewHolders.AreaAdapter;
import com.example.planttracker.viewHolders.AreaViewModel;

public class AreasActivity extends BaseActivity {
    private ActivityAreasBinding binding;
    private AreaViewModel areaViewModel;
    protected static final int NEW_AREA_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAreasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

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
                Intent intent = IntentFactory.editAreaActivityIntentFactory(getApplicationContext(), NEW_AREA_ID);
                startActivity(intent);
            }
        });

        binding.areasActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.mainActivityIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });
    }


}