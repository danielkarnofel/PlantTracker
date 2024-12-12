package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityMainBinding;
import com.example.planttracker.utilities.IntentFactory;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.mainActivityMyPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.plantsActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.mainActivityMyAreasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.areasActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.mainActivityAdminOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.adminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    @Override
    protected User getUserInfo() {
        LiveData<User> userObserver = repository.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.loggedInUser = user;
            if (user != null) {
                invalidateOptionsMenu();
                // Hide the admin button if the user is not an admin
                binding.mainActivityAdminOptionsButton.setVisibility(loggedInUser.isAdmin() ? View.VISIBLE : View.GONE);
            }
        });
        return loggedInUser;
    }
}