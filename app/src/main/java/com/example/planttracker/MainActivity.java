package com.example.planttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    public static final String LOG_TAG = "PLANT_TRACKER"; // Tag for LogCat messages
    static final String MAIN_ACTIVITY_USER_ID_EXTRA_KEY = "com.example.planttracker.MAIN_ACTIVITY_USER_ID_EXTRA_KEY";
    static final String SHARED_PREFERENCES_USER_ID_KEY = "com.example.planttracker.SHARED_PREFERENCES_USER_ID_KEY";
    static final String SAVED_INSTANCE_STATE_USER_ID_KEY = "com.example.planttracker.SAVED_INSTANCE_STATE_USER_ID_KEY";

    private ActivityMainBinding binding;
    AppRepository repository;
    public static final int LOGGED_OUT_USER_ID = -1;
    private static int loggedInUserID = LOGGED_OUT_USER_ID;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());
        loginUser(savedInstanceState);

        updateSharedPreference();
        // If there is no user logged in, start the login activity
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        }

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

    static Intent mainActivityIntentFactory(Context applicationContext, int loggedInUserID) {
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra(MAIN_ACTIVITY_USER_ID_EXTRA_KEY, loggedInUserID);
        return intent;
    }

    public static int getLoggedInUserID() {
        return loggedInUserID;
    }

    public static void setLoggedInUserID(int loggedInUserID) {
        MainActivity.loggedInUserID = loggedInUserID;
    }

    private void loginUser(Bundle savedInstanceState) {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), LOGGED_OUT_USER_ID);

        if (loggedInUserID != LOGGED_OUT_USER_ID && savedInstanceState != null && savedInstanceState.containsKey(SHARED_PREFERENCES_USER_ID_KEY)) {
            loggedInUserID = savedInstanceState.getInt(SHARED_PREFERENCES_USER_ID_KEY, LOGGED_OUT_USER_ID);
        }
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            loggedInUserID = getIntent().getIntExtra(MAIN_ACTIVITY_USER_ID_EXTRA_KEY, LOGGED_OUT_USER_ID);
        }
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            return;
        }
        LiveData<User> userObserver = repository.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.user = user;
            // Re-create the menu once user is logged in
            if (user != null) {
                invalidateOptionsMenu();
            }
            // Hide the admin button if the user is not an admin
            if (user != null) {
                binding.mainActivityAdminOptionsButton.setVisibility(user.isAdmin() ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USER_ID_KEY, loggedInUserID);
        updateSharedPreference();
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.sharedPreferencesUserIDKey), loggedInUserID);
        sharedPrefEditor.apply();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (loggedInUserID == LOGGED_OUT_USER_ID || user == null) {
            return false;
        }

        // Username menu item
        MenuItem usernameItem = menu.findItem(R.id.menuUsernameOption);
        usernameItem.setVisible(true);
        usernameItem.setTitle(user.getUsername());
        usernameItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserID));
                return false;
            }
        });

        // Log Out menu item
        MenuItem logoutItem = menu.findItem(R.id.menuLogoutOption);
        logoutItem.setVisible(true);
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog();
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(MainActivity.this);
        final AlertDialog alertDialog = alertBuilder.create();
        alertBuilder.setMessage("Log Out?");
        alertBuilder.setPositiveButton("Log Out", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                logout();
            }
        });
        alertBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertBuilder.create().show();
    }

    private void logout() {
        loggedInUserID = LOGGED_OUT_USER_ID;
        updateSharedPreference();
        getIntent().putExtra(MAIN_ACTIVITY_USER_ID_EXTRA_KEY, LOGGED_OUT_USER_ID);
        startActivity(LoginActivity.loginActivityIntentFactory(getApplicationContext()));
    }


}