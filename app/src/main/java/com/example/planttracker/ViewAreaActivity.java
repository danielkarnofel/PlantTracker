package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.AreaDAO;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.database.typeConverters.LightLevelTypeConverter;
import com.example.planttracker.databinding.ActivityViewAreaBinding;
import com.example.planttracker.utilities.LightLevel;

public class ViewAreaActivity extends AppCompatActivity {
    private ActivityViewAreaBinding binding;
    private int loggedInUserID = MainActivity.LOGGED_OUT_USER_ID;
    private User loggedInUser;
    private AppRepository repository;

    //TODO: get selectedAreaID from intent extra
    // default value for selected slectedAreaID
    private final int NO_AREA_SELECTED = -1;
    // Set selectedAreaID to default value. Im 80% sure this is overridden with the intent factory.
    // Will need reworking if not the case.
    private int selectedAreaID = NO_AREA_SELECTED;
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
        // Update the display depending on the Area ID
        selectedAreaID = getIntent().getIntExtra(VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY,NO_AREA_SELECTED);
        updateDisplay();
        binding.viewAreaActivityEditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Clicking the edit button will start the EditAreaActivity.
                //boolean isNewArea = false;
                // Extra value being passed in to indicate it is being edited, not a new area.
                Intent editIntent = EditAreaActivity.editAreaActivityIntentFactory(getApplicationContext(), selectedAreaID);
                editIntent.putExtra("IS_NEW_AREA", false);
                startActivity(editIntent);
                //startActivity(EditAreaActivity.editAreaActivityIntentFactory(getApplicationContext(), selectedAreaID));

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
    public void updateDisplay(){
        if (selectedAreaID == NO_AREA_SELECTED){
            /// Default values when no area is selected.
            binding.viewAreaActivityNameTextView.setText(R.string.no_area_selected);
            binding.viewAreaActivityLightLevelTextView.setText(R.string.no_area_selected);
            binding.viewAreaActivityNumberOfPlantsTextView.setText(R.string.no_area_selected);

        }
        else {
            /// An area is selected.
            TextView areaNameTextView = findViewById(R.id.viewAreaActivityNameTextView);
            TextView lightLevelTextView = findViewById(R.id.viewAreaActivityLightLevelTextView);
            TextView numberPLantsTextView = findViewById(R.id.viewAreaActivityNumberOfPlantsTextView);
            /// Getting the values of the selected area. No sure if doing it right.
            LiveData<Area> selectedArea = repository.getAreaByID(selectedAreaID);
            Area currentArea = selectedArea.getValue();
            //assert currentArea != null;
            if (currentArea == null){
                return;
            }
            String areaName = currentArea.getName();
            LightLevel areaLight = currentArea.getLightLevel();
            LightLevelTypeConverter lightToIntConverter = new LightLevelTypeConverter();
            int areaLightInt = lightToIntConverter.convertLightLevelToInt(areaLight);
            int numberOfPlants = currentArea.getPlantCount();
            /// Setting the text views to respective values.
            areaNameTextView.setText(areaName);
            lightLevelTextView.setText(String.valueOf(areaLightInt));
            numberPLantsTextView.setText(String.valueOf(numberOfPlants));

        }

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