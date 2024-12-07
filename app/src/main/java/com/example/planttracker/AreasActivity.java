package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityAreasBinding;

public class AreasActivity extends AppCompatActivity {
    private ActivityAreasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAreasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: implement onClick for Add New Area button

        // TODO: implement onClick for Home button
    }

    // TODO: implement basic intent factory
    public static Intent AreasActivityIntentFactory(Context applicationContext) {
        return null;
    }
}