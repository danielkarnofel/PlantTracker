package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private static final int LOGGED_OUT_USER_ID = -1;
    private int loggedInUserID = LOGGED_OUT_USER_ID;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
        // TODO: temporary user for testing only, should be removed later
//        loggedInUserID = 0;
//        user = new User("testuser", "password");
//        user.setAdmin(true);
        // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -

        // If there is no user logged in, start the login activity
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // If the user is an admin, show the Admin Options button
        binding.mainActivityAdminOptionsButton.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);

        binding.mainActivityMyPlantsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = PlantsActivity.plantsActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.mainActivityMyAreasButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AreasActivity.areasActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        binding.mainActivityAdminOptionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = AdminActivity.adminActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    static Intent mainActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, MainActivity.class);
    }
}