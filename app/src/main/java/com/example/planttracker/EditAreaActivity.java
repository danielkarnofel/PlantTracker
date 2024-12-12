package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.planttracker.databinding.ActivityEditAreaBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.LightLevel;

public class EditAreaActivity extends BaseActivity {
    private ActivityEditAreaBinding binding;

    private int selectedAreaID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: get selectedAreaID extra from intent
        selectedAreaID = 1;

        // TODO: save the user inputs to the selected Area
        binding.editAreaActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Use this to get the light level from the selected radio button, returns null if nothing selected yet
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editAreaActivityLightLevelRadioGroup);

                // Clicking the save button will start the ViewAreaActivity.
                startActivity(IntentFactory.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        // TODO: If creating a new area, should return to AreasActivity, if editing an existing area,
        //  should return to ViewAreaActivity and pass the selectedAreaID as an extra
        binding.editAreaActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the cancel button will start the ViewAreaActivity.
                startActivity(IntentFactory.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        // TODO: delete the selected area
        binding.editAreaActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the delete button will send user back to AreasActivity.
                startActivity(IntentFactory.areasActivityIntentFactory(getApplicationContext()));
            }
        });

    }


}