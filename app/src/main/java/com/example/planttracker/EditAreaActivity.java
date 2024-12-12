package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityEditAreaBinding;
import com.example.planttracker.utilities.LightLevel;

import java.util.Objects;

public class EditAreaActivity extends AppCompatActivity {
    private ActivityEditAreaBinding binding;
    static final String EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY = "com.example.planttracker.EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY";
    private int loggedInUserID;
    private User loggedInUser;
    private AppRepository repository;
    /// Can be deleted later, default values if not area is selected.
    private final int NO_AREA_SELECTED = -1;
    private int selectedAreaID = NO_AREA_SELECTED;

    // TODO: add a boolean that indicates whether a new area is being created, or an existing area is being edited
    private final boolean IS_NEW= true;
    private boolean isNewArea = IS_NEW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditAreaBinding.inflate(getLayoutInflater());
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

        // TODO: get selectedAreaID extra from intent
        selectedAreaID = getIntent().getIntExtra(EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY,NO_AREA_SELECTED);
        /// Getting passed in extra from onClick of ViewAreaActivity. Will set isNewArea to false.
        /// Key IS_NEW_AREA paired to boolean value false in ViewAreaActivity.
        Bundle extra = getIntent().getExtras();
        if (extra != null){
            isNewArea = extra.getBoolean("IS_NEW_AREA");
        }

        // TODO: save the user inputs to the selected Area
        binding.editAreaActivitySaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // TODO: Use this to get the light level from the selected radio button, returns null if nothing selected yet
                LightLevel selectedLightLevel = LightLevel.getSelectionFromRadioGroup(binding.getRoot(), binding.editAreaActivityLightLevelRadioGroup);
                EditText areaText = findViewById(R.id.editAreaActivityNameEditText);
                String inputAreaName = areaText.getText().toString();
                if ((selectedLightLevel != null) && !inputAreaName.isEmpty() && isNewArea){
                    // Zero plants in this area as it is a new area?
                    Area area = new Area(loggedInUserID, inputAreaName, 0, selectedLightLevel);
                    selectedAreaID = area.getAreaID();
                    repository.insertArea(area);
                }
                startActivity(ViewAreaActivity.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });
        // TODO: If creating a new area, should return to AreasActivity, if editing an existing area,
        //  should return to ViewAreaActivity and pass the selectedAreaID as an extra
        binding.editAreaActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the cancel button will start the ViewAreaActivity.
                startActivity(ViewAreaActivity.viewAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));
            }
        });

        // TODO: delete the selected area
        binding.editAreaActivityDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the delete button will send user back to AreasActivity.
                /// Not sure if this is correct. To delete area from repository.
                repository.deleteArea(repository.getAreaByID(selectedAreaID).getValue());
                startActivity(AreasActivity.areasActivityIntentFactory(getApplicationContext()));
            }
        });

    }

    public static Intent editAreaActivityIntentFactory(Context applicationContext, int selectedAreaID) {
        Intent intent = new Intent(applicationContext, EditAreaActivity.class);
        intent.putExtra(EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, selectedAreaID);
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