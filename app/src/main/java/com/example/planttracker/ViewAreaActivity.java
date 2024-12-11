package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityViewAreaBinding;

public class ViewAreaActivity extends AppCompatActivity {
    private ActivityViewAreaBinding binding;
    private int loggedInUserID = MainActivity.LOGGED_OUT_USER_ID;
    private User loggedInUser;
    private AppRepository repository;
    private int selectedAreaID;
    static final String VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY = "com.example.planttracker.VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewAreaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        // Get userID from shared preferences
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), MainActivity.LOGGED_OUT_USER_ID);

        // Pull user info from database
        LiveData<User> userObserver = repository.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.loggedInUser = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });

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

    // Menu functions

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (loggedInUserID == MainActivity.LOGGED_OUT_USER_ID || loggedInUser == null) {
            return false;
        }

        // Username menu item
        MenuItem usernameItem = menu.findItem(R.id.menuUsernameOption);
        usernameItem.setVisible(true);
        usernameItem.setTitle(loggedInUser.getUsername());
        usernameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserID));
                return false;
            }
        });

        // hide Log Out menu item
        MenuItem logoutItem = menu.findItem(R.id.menuLogoutOption);
        logoutItem.setVisible(false);

        return true;
    }
}