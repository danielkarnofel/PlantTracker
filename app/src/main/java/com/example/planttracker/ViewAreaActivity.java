package com.example.planttracker;

import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.databinding.ActivityViewAreaBinding;
import com.example.planttracker.utilities.IntentFactory;

public class ViewAreaActivity extends BaseActivity {
    private ActivityViewAreaBinding binding;
    private int selectedAreaID = AreasActivity.NEW_AREA_ID;
    private Area selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedAreaID = getIntent().getIntExtra(IntentFactory.VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, AreasActivity.NEW_AREA_ID);

        LiveData<Area> areaObserver = repository.getAreaByAreaID(selectedAreaID);
        areaObserver.observe(this, area -> {
            selectedArea = area;
            if (selectedArea != null) {
                updateDisplay();
            }
        });

        binding.viewAreaActivityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.editAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        binding.viewAreaActivityBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.areasActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    public void updateDisplay() {
        binding.viewAreaActivityNameTextView.setText(selectedArea.getName());
        binding.viewAreaActivityLightLevelTextView.setText(selectedArea.getLightLevel().toString());
        binding.viewAreaActivityNumberOfPlantsTextView.setText(String.valueOf(selectedArea.getPlantCount()));
    }

}