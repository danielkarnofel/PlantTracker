package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    // TODO: loggedInUserID should be -1 by default, setting to 0 for testing
    private int loggedInUserID = 0;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // TODO: temporary user for testing only
        user = new User("testuser", "password");
        user.setAdmin(false);

        if (loggedInUserID == -1) {
            Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        binding.viewPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PlantsActivity.plantsActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.viewAreasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AreasActivity.areasActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.adminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!user.isAdmin()) {
                    return; // TODO: defensive, but is this check necessary?
                }
                Intent intent = AdminActivity.adminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    static Intent mainActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, MainActivity.class);
    }
}