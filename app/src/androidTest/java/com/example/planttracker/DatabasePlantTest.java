package com.example.planttracker;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.planttracker.database.AppDatabase;
import com.example.planttracker.database.AreaDAO;
import com.example.planttracker.database.PlantDAO;
import com.example.planttracker.database.UserDAO;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.utilities.LightLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.time.LocalDateTime;

@RunWith(AndroidJUnit4.class)
public class DatabasePlantTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static AppDatabase db;
    private static UserDAO userDAO;
    private static AreaDAO areaDAO;
    private static PlantDAO plantDAO;
    private Plant testPlant;
    private LiveData<Plant> plantObserver;

    @Before
    public void CreateDB() {

        plantObserver = null;
        testPlant = null;

        db = Room.inMemoryDatabaseBuilder(getApplicationContext(), AppDatabase.class).build();
        userDAO = db.userDAO();
        areaDAO = db.areaDAO();
        plantDAO = db.plantDAO();

        // Create default users:
        User testAdmin1 = new User("admin1", "admin1", true);
        User testUser1 = new User("testUser1", "testUser1");

        userDAO.insert(testAdmin1, testUser1);

        // Create default areas
        Area testArea1 = new Area(1, "Living Room", 1, LightLevel.MEDIUM);
        Area testArea2 = new Area(1, "Bedroom", 1, LightLevel.LOW);
        Area testArea3 = new Area(1, "Balcony", 1, LightLevel.HIGH);

        areaDAO.insert(testArea1, testArea2, testArea3);

        // Create default plants
        Plant testPlant1 = new Plant(1, 1, "Mr. Leaves", "Fern", LightLevel.LOW, 3, LocalDateTime.now());
        Plant testPlant2 = new Plant(1, 2, "Leaf Erikson", "Shrub", LightLevel.MEDIUM, 7, LocalDateTime.now());
        Plant testPlant3 = new Plant(1, 3, "Cooler Cactus", "Succulent", LightLevel.HIGH, 14, LocalDateTime.now());

        plantDAO.insert(testPlant1, testPlant2, testPlant3);



        testPlant = new Plant(1, 1, "Testy", "Tester", LightLevel.LOW, 7, LocalDateTime.now());

        LiveData<Plant> plantObserver = plantDAO.getPlantByID(4);
        try {
            LiveDataTestUtil.getOrAwaitValue(plantObserver);
        } catch (InterruptedException ignored) {

        }
        assertNull(plantObserver.getValue());
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void testPlantCreate() throws InterruptedException {

        int newID = (int) plantDAO.insert(testPlant);
        testPlant.setPlantID(newID);

        plantObserver = plantDAO.getPlantByID(newID);
        LiveDataTestUtil.getOrAwaitValue(plantObserver);
        assertNotNull(plantObserver.getValue());
        assertEquals(plantObserver.getValue().getPlantID(), testPlant.getPlantID());
        assertEquals(plantObserver.getValue().getAreaID(), testPlant.getAreaID());
        assertEquals(plantObserver.getValue().getName(), testPlant.getName());
        assertEquals(plantObserver.getValue().getType(), testPlant.getType());
        assertEquals(plantObserver.getValue().getLightLevelNeeded(), testPlant.getLightLevelNeeded());
        assertEquals(plantObserver.getValue().getWateringFrequency(), testPlant.getWateringFrequency());
    }

    @Test
    public void testPlantUpdate() throws InterruptedException {

        int newID = (int) plantDAO.insert(testPlant);
        testPlant.setPlantID(newID);

        plantObserver = plantDAO.getPlantByID(newID);
        LiveDataTestUtil.getOrAwaitValue(plantObserver);
        assertNotNull(plantObserver.getValue());

        testPlant.setName("Not Testy");
        plantDAO.update(testPlant);

        LiveData<Plant> updatedPlantObserver = plantDAO.getPlantByID(newID);
        LiveDataTestUtil.getOrAwaitValue(updatedPlantObserver);
        assertNotNull(updatedPlantObserver.getValue());
        assertEquals(updatedPlantObserver.getValue().getName(), testPlant.getName());
    }

    @Test
    public void testPlantDelete() throws InterruptedException {

        int newID = (int) plantDAO.insert(testPlant);
        testPlant.setPlantID(newID);

        plantDAO.delete(testPlant);
        LiveData<Plant> updatedPlantObserver = plantDAO.getPlantByID(newID);
        LiveDataTestUtil.getOrAwaitValue(updatedPlantObserver);
        assertNull(updatedPlantObserver.getValue());
    }
}
