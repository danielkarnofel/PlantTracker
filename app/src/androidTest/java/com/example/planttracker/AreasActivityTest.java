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
public class AreasActivityTest {

    @Rule
    public ActivityScenarioRule<AreasActivity> activityScenarioRule =
            new ActivityScenarioRule<>(AreasActivity.class);

    @Before
    public void setup() {
        Intents.init();
    }

    @After
    public void teardown() {
        Intents.release();
    }

    @Test
    public void testAreasActivityIntentFactory() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = IntentFactory.areasActivityIntentFactory(context);
        assertNotNull(intent);
        assertEquals(AreasActivity.class.getName(), intent.getComponent().getClassName());
        // Areas activity is opened.
        assertEquals(-1, intent.getIntExtra(IntentFactory.USER_ID_EXTRA_KEY, -1));
    }

}
