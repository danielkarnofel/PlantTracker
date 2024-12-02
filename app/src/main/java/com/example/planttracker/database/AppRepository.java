package com.example.planttracker.database;

import android.app.Application;

public class AppRepository {
    private final AppDAO appDAO;
    private static AppRepository repository;

    private AppRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        this.appDAO = db.appDAO();
    }

    // TODO: implement getRepository method using singleton patter and a Future
    public static AppRepository getRepository(Application application) {
        return null;
    }
}
