package com.example.planttracker.utilities;

import android.content.Context;
import android.content.Intent;

import com.example.planttracker.AdminActivity;
import com.example.planttracker.AreasActivity;
import com.example.planttracker.EditAreaActivity;
import com.example.planttracker.EditPlantActivity;
import com.example.planttracker.LoginActivity;
import com.example.planttracker.MainActivity;
import com.example.planttracker.PlantsActivity;
import com.example.planttracker.SignUpActivity;
import com.example.planttracker.ViewAreaActivity;
import com.example.planttracker.ViewPlantActivity;

public class IntentFactory {
    public static final String EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY = "com.example.planttracker.EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY";
    public static final String VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY = "com.example.planttracker.VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY";
    public static final String EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY = "com.example.planttracker.EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY";
    public static final String VIEW_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY = "com.example.planttracker.VIEW_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY";
    public static final String USER_ID_EXTRA_KEY = "com.example.planttracker.USER_ID_EXTRA_KEY";

    public static Intent loginActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, LoginActivity.class);
    }

    public static Intent signupActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, SignUpActivity.class);
    }

    public static Intent mainActivityIntentFactory(Context applicationContext, int loggedInUserID) {
        Intent intent = new Intent(applicationContext, MainActivity.class);
        intent.putExtra(USER_ID_EXTRA_KEY, loggedInUserID);
        return intent;
    }

    public static Intent adminActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, AdminActivity.class);
    }

    public static Intent areasActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, AreasActivity.class);
    }

    public static Intent viewAreaActivityIntentFactory(Context applicationContext, int selectedAreaID){
        Intent intent = new Intent(applicationContext, ViewAreaActivity.class);
        intent.putExtra(VIEW_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, selectedAreaID);
        return intent;
    }

    public static Intent editAreaActivityIntentFactory(Context applicationContext, int selectedAreaID) {
        Intent intent = new Intent(applicationContext, EditAreaActivity.class);
        intent.putExtra(EDIT_AREA_ACTIVITY_SELECTED_AREA_ID_EXTRA_KEY, selectedAreaID);
        return intent;
    }

    public static Intent plantsActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, PlantsActivity.class);
    }

    public static Intent viewPlantActivityIntentFactory(Context applicationContext, int selectedPlantID){
        Intent intent = new Intent(applicationContext, ViewPlantActivity.class);
        intent.putExtra(VIEW_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, selectedPlantID);
        return intent;
    }

    public static Intent editPlantActivityIntentFactory(Context applicationContext, int selectedPlantID) {
        Intent intent = new Intent(applicationContext, EditPlantActivity.class);
        intent.putExtra(EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, selectedPlantID);
        return intent;
    }
}
