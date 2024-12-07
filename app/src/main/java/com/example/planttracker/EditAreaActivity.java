package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityEditAreaBinding;

public class EditAreaActivity extends AppCompatActivity {
    private ActivityEditAreaBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: implement onClick for Save button

        // TODO: implement onClick for Cancel button

        // TODO: implement onClick for Delete button
    }

    // TODO: implement basic intent factory
    public static Intent editAreaActivityIntentFactory(Context applicationContext) {
        return null; // placeholder to make code compile
    }
}