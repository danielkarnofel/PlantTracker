package com.example.planttracker;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.intent.Intents;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.planttracker.utilities.IntentFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class EditPlantActivityTest {

    @Rule
    public ActivityScenarioRule<EditPlantActivity> activityScenarioRule =
            new ActivityScenarioRule<>(EditPlantActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void testEditPlantActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = IntentFactory.editPlantActivityIntentFactory(context,198);
        assertNotNull(intent);
        assertEquals(EditPlantActivity.class.getName(), intent.getComponent().getClassName());
        assertEquals(198, intent.getIntExtra(IntentFactory.EDIT_PLANT_ACTIVITY_SELECTED_PLANT_ID_EXTRA_KEY, -1));
    }

}
