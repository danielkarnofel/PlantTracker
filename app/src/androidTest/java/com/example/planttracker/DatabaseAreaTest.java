package com.example.planttracker;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;

import android.content.Context;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.example.planttracker.database.AppDatabase;
import com.example.planttracker.database.AreaDAO;
import com.example.planttracker.database.UserDAO;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.utilities.LightLevel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

@RunWith(AndroidJUnit4.class)
public class DatabaseAreaTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static AppDatabase db;
    private static UserDAO userDAO;
    private static AreaDAO areaDAO;

    @Before
    public void CreateDB() {
        Context context = getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDAO = db.userDAO();
        areaDAO = db.areaDAO();

        LiveData<User> userObserver;

        // Create default users:
        User testAdmin1 = new User("admin1", "admin1", true);
        User testUser1 = new User("testUser1", "testUser1");

        userDAO.insert(testAdmin1, testUser1);

        // Create default areas
        Area testArea1 = new Area(1, "Living Room", 1, LightLevel.MEDIUM);
        Area testArea2 = new Area(1, "Bedroom", 1, LightLevel.LOW);
        Area testArea3 = new Area(1, "Balcony", 1, LightLevel.HIGH);

        areaDAO.insert(testArea1, testArea2, testArea3);
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void testAreaCreate() throws Exception {
        LiveData<User> userObserver;
        LiveData<List<Area>> areaObserver;

        String username = "admin1";
        String areaName = "Patio";
        int plantCount = 2;
        LightLevel lightLevel = LightLevel.MEDIUM;

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue()); // should return true

        User user = userObserver.getValue();
        Area area = new Area(user.getUserID(), areaName, plantCount, lightLevel);

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 3); // should return true

        // add an admin
        areaDAO.insert(area);

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 4);
    }

    @Test
    public void testAreaUpdate() throws Exception {
        LiveData<User> userObserver;
        LiveData<List<Area>> areaObserver;

        String username = "admin1";
        String areaName = "Balcony"; // area to update
        int plantCount = 2; // increase plant count
        LightLevel lightLevel = LightLevel.MEDIUM; // update light level

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue()); // should return true

        User user = userObserver.getValue();

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 3); // should return true

        Area area = areaObserver.getValue().stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        assertNotNull(area);
        assertNotEquals(area.getPlantCount(), plantCount);
        assertNotEquals(area.getLightLevel(), lightLevel);

        area.setPlantCount(plantCount);
        area.setLightLevel(lightLevel);

        areaDAO.insert(area);

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 3);

        area = areaObserver.getValue().stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        assertNotNull(area);
        assertEquals(area.getPlantCount(), plantCount);
        assertEquals(area.getLightLevel(), lightLevel);
    }

    @Test
    public void testAreaDelete() throws Exception {
        LiveData<User> userObserver;
        LiveData<List<Area>> areaObserver;

        String username = "admin1";
        String areaName = "Balcony"; // area to delete

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue()); // should return true

        User user = userObserver.getValue();

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 3); // should return true

        Area area = areaObserver.getValue().stream().filter(a -> a.getName().equals(areaName)).findFirst().orElse(null);

        assertNotNull(area);

        areaDAO.delete(area);

        areaObserver = areaDAO.getAllAreasByUserID(user.getUserID());
        LiveDataTestUtil.getOrAwaitValue(areaObserver);
        assertEquals(areaObserver.getValue().size(), 2);
    }
}
