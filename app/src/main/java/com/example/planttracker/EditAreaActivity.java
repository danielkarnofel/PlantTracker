package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityEditAreaBinding;

public class EditAreaActivity extends AppCompatActivity {
    private ActivityEditAreaBinding binding;
    private int loggedInUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // TODO: implement onClick for save button
        // todo is not complete, more work on this button might be needed.
        binding.editAreaActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the save button will start the ViewAreaActivity.
                startActivity(ViewAreaActivity.viewAreaActivityIntentFactory(getApplicationContext()));
            }
        });

        // TODO: implement onClick for Cancel button
        // todo is not complete, more work on this button might be needed.
        binding.editAreaActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the cancel button will start the ViewAreaActivity.
                startActivity(ViewAreaActivity.viewAreaActivityIntentFactory(getApplicationContext()));
            }
        });
        // TODO: implement onClick for Delete button
        /** Commented out, implemented prior to the implementation of AreasActivityIntentFactory.
        binding.editAreaActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        // Clicking the delete button will send user back to AreasActivity.
        startActivity(AreasActivity.AreasActivityIntentFactory(getApplicationContext()));
        }
        });
         */

    }

    // TODO: implement basic intent factory
    public static Intent editAreaActivityIntentFactory(Context applicationContext) {
        return null; // placeholder to make code compile
    }
}