package com.example.planttracker.database;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.planttracker.database.entities.User;

public class AppRepository {
    private final AreaDAO areaDAO;
    private final PlantDAO plantDAO;
    private final UserDAO userDAO;
    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.areaDAO = db.areaDAO();
        this.plantDAO = db.plantDAO();
        this.userDAO = db.userDAO();
    }

    // TODO: implement getRepository method using singleton patter and a Future

    /// *** Code borrowed from HW4 ***
    public void insertUser(User... user){
        AppDatabase.databaseWriteExecutor.execute(() -> {
            userDAO.insert(user);
        });
    }

    public LiveData<User> getUserByUserName(String username) {
        return userDAO.getUserByUserName(username);
    }
    public LiveData<User> getUserByUserId(int userId) {
        return userDAO.getUserByUserId(userId);
    }
    /// *** End code borrowed from H4 ***
    public static AppRepository getRepository(Application application) {
        return null;
    }
}
