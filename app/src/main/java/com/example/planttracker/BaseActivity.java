package com.example.planttracker;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.utilities.IntentFactory;

public class BaseActivity extends AppCompatActivity {

    public static final String LOG_TAG = "PLANT_TRACKER"; // Tag for LogCat messages
    static final String SHARED_PREFERENCES_USER_ID_KEY = "com.example.planttracker.SHARED_PREFERENCES_USER_ID_KEY";
    static final String SAVED_INSTANCE_STATE_USER_ID_KEY = "com.example.planttracker.SAVED_INSTANCE_STATE_USER_ID_KEY";

    AppRepository repository;
    static final int LOGGED_OUT_USER_ID = -1;
    static int loggedInUserID = LOGGED_OUT_USER_ID;
    protected User loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        repository = AppRepository.getRepository(getApplication());

        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            // Priority 1: Check Intent Extras
            getLoggedInUserIDWithExtra();
        }
        if (loggedInUserID == LOGGED_OUT_USER_ID && savedInstanceState != null) {
            // Priority 2: Check Saved Instance State
            getLoggedInUserIDWithSavedInstanceState(savedInstanceState);
        }
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            // Priority 3: Check SharedPreferences
            getLoggedInUserIDWithSharedPreferences();
        }

        updateSharedPreference();

        // If there is still no user logged in, start the login activity
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            Intent intent = IntentFactory.loginActivityIntentFactory(getApplicationContext());
            startActivity(intent);
        }

        // Pull user info from database
        loggedInUser = getUserInfo();
    }

    protected User getUserInfo() {
        LiveData<User> userObserver = repository.getUserByUserID(loggedInUserID);
        userObserver.observe(this, user -> {
            this.loggedInUser = user;
            if (user != null) {
                invalidateOptionsMenu();
            }
        });
        return loggedInUser;
    }

    private void getLoggedInUserIDWithSharedPreferences() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        loggedInUserID = sharedPreferences.getInt(getString(R.string.sharedPreferencesUserIDKey), LOGGED_OUT_USER_ID);
    }

    private void getLoggedInUserIDWithSavedInstanceState(Bundle savedInstanceState) {
        if (loggedInUserID != LOGGED_OUT_USER_ID && savedInstanceState != null && savedInstanceState.containsKey(SHARED_PREFERENCES_USER_ID_KEY)) {
            loggedInUserID = savedInstanceState.getInt(SHARED_PREFERENCES_USER_ID_KEY, LOGGED_OUT_USER_ID);
        }
    }

    private void getLoggedInUserIDWithExtra() {
        if (loggedInUserID == LOGGED_OUT_USER_ID) {
            loggedInUserID = getIntent().getIntExtra(IntentFactory.USER_ID_EXTRA_KEY, LOGGED_OUT_USER_ID);
        }
    }

    private void updateSharedPreference() {
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences(getString(R.string.sharedPreferencesFileName), Context.MODE_PRIVATE);
        SharedPreferences.Editor sharedPrefEditor = sharedPreferences.edit();
        sharedPrefEditor.putInt(getString(R.string.sharedPreferencesUserIDKey), loggedInUserID);
        sharedPrefEditor.apply();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_INSTANCE_STATE_USER_ID_KEY, loggedInUserID);
        updateSharedPreference();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.user_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        if (loggedInUserID == LOGGED_OUT_USER_ID || loggedInUser == null) {
            return false;
        }

        // Username menu item
        MenuItem usernameItem = menu.findItem(R.id.menuUsername);
        usernameItem.setVisible(true);
        usernameItem.setTitle(loggedInUser.getUsername());
        usernameItem.setEnabled(false);

        // Home menu item
        MenuItem homeItem = menu.findItem(R.id.menuHomeOption);
        homeItem.setVisible(true);
        homeItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                startActivity(IntentFactory.mainActivityIntentFactory(getApplicationContext(), loggedInUserID));
                return false;
            }
        });

        // Log Out menu item
        MenuItem logoutItem = menu.findItem(R.id.menuLogoutOption);
        logoutItem.setVisible(true);
        logoutItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                showLogoutDialog(BaseActivity.this);
                return false;
            }
        });
        return true;
    }

    private void showLogoutDialog(Context context) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
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
        getIntent().putExtra(IntentFactory.USER_ID_EXTRA_KEY, LOGGED_OUT_USER_ID);
        startActivity(IntentFactory.loginActivityIntentFactory(getApplicationContext()));
    }
}