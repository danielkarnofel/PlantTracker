package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityEditAreaBinding;

public class EditAreaActivity extends AppCompatActivity {
    private ActivityEditAreaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        // TODO: implement onClick for save button
        // todo is not complete, more work on this button might be needed.
        binding.editAreaSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the save button will start the ViewAreaActivity.
                startActivity(ViewAreaActivity.ViewAreaActivityIntentFactory(getApplicationContext()));
            }
        });

        // TODO: implement onClick for Cancel button
        // todo is not complete, more work on this button might be needed.
        binding.editAreaCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the cancel button will start the ViewAreaActivity.
                startActivity(ViewAreaActivity.ViewAreaActivityIntentFactory(getApplicationContext()));
            }
        });
        // TODO: implement onClick for Delete button
        /** Commented out, implemented prior to the implementation of AreasActivityIntentFactory.
        binding.editAreaDeleteButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
        // Clicking the delete button will send user back to AreasActivity.
        startActivity(AreasActivity.AreasActivityIntentFactory(getApplicationContext()));
        }
        });
         */

    }

    static Intent EditAreaActivityIntentFactory(Context context){
        return new Intent(context, EditAreaActivity.class);
    }
}