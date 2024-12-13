package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityEditAreaBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.utilities.LightLevel;

public class EditAreaActivity extends BaseActivity {
    private ActivityEditAreaBinding binding;
    private int selectedAreaID;
    private Area selectedArea;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        selectedAreaID = getIntent().getIntExtra(IntentFactory.EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, AreasActivity.NEW_AREA_ID);

        if (selectedAreaID != AreasActivity.NEW_AREA_ID) {
            autofillValues();
        }

        binding.editAreaActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.editAreaActivityNameEditText.getText().toString();
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editAreaActivityLightLevelRadioGroup);

                if (selectedAreaID == AreasActivity.NEW_AREA_ID) {
                    repository.insertArea(new Area(loggedInUserID, name, 0, selectedLightLevel));
                } else {
                    LiveData<Area> areaObserver = repository.getAreaByAreaID(selectedAreaID);
                    areaObserver.observe(EditAreaActivity.this, area -> {
                        selectedArea = area;
                        if (selectedArea != null) {
                            selectedArea.setName(name);
                            selectedArea.setLightLevel(selectedLightLevel);
                            repository.updateArea(selectedArea);
                        }
                    });
                }
                startActivity(IntentFactory.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        binding.editAreaActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(IntentFactory.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        binding.editAreaActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedAreaID != AreasActivity.NEW_AREA_ID) {
                    LiveData<Area> areaObserver = repository.getAreaByAreaID(selectedAreaID);
                    areaObserver.observe(EditAreaActivity.this, area -> {
                        selectedArea = area;
                        if (selectedArea != null) {
                            repository.deleteArea(selectedArea);
                        }
                    });
                }
                startActivity(IntentFactory.areasActivityIntentFactory(getApplicationContext()));
            }
        });

    }

    private void autofillValues() {

    }
}