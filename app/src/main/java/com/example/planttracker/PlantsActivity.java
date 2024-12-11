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
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityPlantsBinding;
import com.example.planttracker.viewHolders.PlantAdapter;
import com.example.planttracker.viewHolders.PlantViewModel;

public class PlantsActivity extends AppCompatActivity {
    private ActivityPlantsBinding binding;
    private int loggedInUserID;
    private User loggedInUser;
    private AppRepository repository;
    private PlantViewModel plantViewModel;
    public static final int NEW_PLANT_ID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPlantsBinding.inflate(getLayoutInflater());
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


        // Set up Plant recycler view
        plantViewModel = new ViewModelProvider(this).get(PlantViewModel.class);
        RecyclerView recyclerView = binding.plantsActivityRecyclerView;
        final PlantAdapter adapter = new PlantAdapter(new PlantAdapter.PlantDiff());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView.setLayoutManager(layoutManager);
        LinearSnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
        plantViewModel.getAllPlantsByUserID(loggedInUserID).observe(this, adapter::submitList);

        binding.plantsActivityAddNewPlantButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = EditPlantActivity.editPlantActivityIntentFactory(getApplicationContext(), NEW_PLANT_ID);
                startActivity(intent);
            }
        });

        binding.plantsActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = MainActivity.mainActivityIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });
    }

    public static Intent plantsActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, PlantsActivity.class);
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