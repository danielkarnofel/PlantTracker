package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityAreasBinding;

public class AreasActivity extends AppCompatActivity {
    private ActivityAreasBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAreasBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.addNewAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditAreaActivity.editAreaActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    // TODO: implement basic intent factory
    public static Intent areasActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, AreasActivity.class);
    }
}