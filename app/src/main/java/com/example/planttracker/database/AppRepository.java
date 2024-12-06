package com.example.planttracker.database;

import android.app.Application;

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
    public static AppRepository getRepository(Application application) {
        return null;
    }
}
