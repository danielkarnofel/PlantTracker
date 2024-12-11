package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityViewAreaBinding;

public class ViewAreaActivity extends AppCompatActivity {
    private ActivityViewAreaBinding binding;
    private int loggedInUserID = MainActivity.LOGGED_OUT_USER_ID;
    private int selectedAreaID;
    static final String VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY = "com.example.planttracker.VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        //TODO: get selectedAreaID from intent extra
        selectedAreaID = 1;

        binding.viewAreaActivityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the edit button will start the EditAreaActivity.
                startActivity(EditAreaActivity.editAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        // implement onClick for Back button
        binding.viewAreaActivityBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the back button will send user back to AreasActivity.
                startActivity(AreasActivity.areasActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    public static Intent viewAreaActivityIntentFactory(Context applicationContext, int selectedAreaID){
        Intent intent = new Intent(applicationContext, ViewAreaActivity.class);
        intent.putExtra(VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, selectedAreaID);
        return intent;
    }
}