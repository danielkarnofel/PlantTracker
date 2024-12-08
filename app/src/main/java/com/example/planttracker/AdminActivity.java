package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.planttracker.databinding.ActivityAdminBinding;

public class AdminActivity extends AppCompatActivity {
    static final String ADMIN_ACTIVITY_USER_ID_EXTRA_KEY = "com.example.planttracker.ADMIN_ACTIVITY_USER_ID_EXTRA_KEY";
    private ActivityAdminBinding binding;
    private int loggedInUserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);


        // TODO: implement onClick for Home button
    }

    // TODO: implement basic intent factory
    public static Intent adminActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, AdminActivity.class);
    }
}