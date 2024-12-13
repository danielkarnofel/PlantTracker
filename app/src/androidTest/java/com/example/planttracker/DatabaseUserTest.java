package com.example.planttracker;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
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
public class DatabaseUserTest {
    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();
    private static AppDatabase db;
    private static UserDAO userDAO;
    private static AreaDAO areaDAO;
    private static PlantDAO plantDAO;

    @Before
    public void CreateDB() {
        Context context = getApplicationContext();

        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        userDAO = db.userDAO();
        areaDAO = db.areaDAO();
        plantDAO = db.plantDAO();

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

        // Create default plants
        Plant testPlant1 = new Plant(1, 1, "Mr. Leaves", "Fern", LightLevel.LOW, 3, LocalDateTime.now());
        Plant testPlant2 = new Plant(1, 2, "Leaf Erikson", "Shrub", LightLevel.MEDIUM, 7, LocalDateTime.now());
        Plant testPlant3 = new Plant(1, 3, "Cooler Cactus", "Succulent", LightLevel.HIGH, 14, LocalDateTime.now());

        plantDAO.insert(testPlant1, testPlant2, testPlant3);
    }

    @After
    public void closeDB() {
        db.close();
    }

    @Test
    public void testAdminUserCreate() throws Exception {
        LiveData<User> userObserver;

        String username = "admin2";
        String password = "admin2";
        boolean isAdmin = true;

        User user = new User(username, password, isAdmin);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue()); // should return true

        // add an admin
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), user.getUsername());
        assertEquals(userObserver.getValue().getPassword(), user.getPassword());
        assertEquals(userObserver.getValue().isAdmin(), user.isAdmin());
    }

    @Test
    public void testAdminUserUpdate() throws Exception {
        LiveData<User> userObserver;

        String usernameToUpdate = "admin1";

        String username = "admin11";
        String password = "admin123";

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue()); // should return true

        // get an admin user from the database
        userObserver = userDAO.getUserByUsername(usernameToUpdate);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());

        User user = userObserver.getValue();
        // update the admin user information
        user.setUsername(username);
        user.setPassword(password);

        // make the admin user changes persistent
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), user.getUsername());
        assertEquals(userObserver.getValue().getPassword(), user.getPassword());
        assertEquals(userObserver.getValue().isAdmin(), user.isAdmin());
    }

    @Test
    public void testAdminToNormal() throws Exception {
        LiveData<User> userObserver;

        String username = "admin1";
        boolean isAdmin = false;

        // get an admin user from the database
        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());

        User user = userObserver.getValue();
        assertNotEquals(userObserver.getValue().isAdmin(), isAdmin);
        // update the user information
        user.setAdmin(isAdmin);

        // make the user changes persistent
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), user.getUsername());
        assertEquals(userObserver.getValue().getPassword(), user.getPassword());
        assertEquals(userObserver.getValue().isAdmin(), user.isAdmin());
    }

    @Test
    public void testAdminUserDelete() throws Exception {
        LiveData<User> userObserver;

        String username = "admin1";

        // get a normal user from the database
        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());
        User user = userObserver.getValue();

        // make the user changes persistent
        userDAO.delete(user);

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue());
    }

    @Test
    public void testNormalUserCreate() throws Exception {
        LiveData<User> userObserver;

        String username = "testuser2";
        String password = "testuser2";
        boolean isAdmin = false;

        User user = new User(username, password, isAdmin);

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue()); // should return true

        // add a normal user
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), username);
        assertEquals(userObserver.getValue().getPassword(), password);
        assertEquals(userObserver.getValue().isAdmin(), isAdmin);
    }

    @Test
    public void testNormalUserUpdate() throws Exception {
        LiveData<User> userObserver;

        String usernameToUpdate = "testUser1";

        String username = "testUser11";
        String password = "testUser123";

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue()); // should return true

        // get a normal user from the database
        userObserver = userDAO.getUserByUsername(usernameToUpdate);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());

        User user = userObserver.getValue();
        // update the user information
        user.setUsername(username);
        user.setPassword(password);

        // make the user changes persistent
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), user.getUsername());
        assertEquals(userObserver.getValue().getPassword(), user.getPassword());
        assertEquals(userObserver.getValue().isAdmin(), user.isAdmin());
    }

    @Test
    public void testNormalToAdmin() throws Exception {
        LiveData<User> userObserver;

        String username = "testUser1";
        boolean isAdmin = true;

        // get a normal user from the database
        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());
        User user = userObserver.getValue();
        assertNotEquals(userObserver.getValue().isAdmin(), isAdmin);
        // update the user information
        user.setAdmin(isAdmin);

        // make the user changes persistent
        userDAO.insert(user);

        userObserver = userDAO.getUserByUsername(user.getUsername());
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNotNull(userObserver.getValue());
        assertEquals(userObserver.getValue().getUsername(), user.getUsername());
        assertEquals(userObserver.getValue().getPassword(), user.getPassword());
        assertEquals(userObserver.getValue().isAdmin(), user.isAdmin());
    }

    @Test
    public void testNormalUserDelete() throws Exception {
        LiveData<User> userObserver;

        String username = "testUser1";

        // get a normal user from the database
        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);

        assertNotNull(userObserver.getValue());
        User user = userObserver.getValue();

        // make the user changes persistent
        userDAO.delete(user);

        userObserver = userDAO.getUserByUsername(username);
        LiveDataTestUtil.getOrAwaitValue(userObserver);
        assertNull(userObserver.getValue());
    }
}
