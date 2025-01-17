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
public class PlantsActivityTest {

    @Rule
    public ActivityScenarioRule<PlantsActivity> activityScenarioRule =
            new ActivityScenarioRule<>(PlantsActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void testPlantsActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = IntentFactory.plantsActivityIntentFactory(context);

        assertNotNull(intent);
        assertEquals(PlantsActivity.class.getName(), intent.getComponent().getClassName());
    }

}
