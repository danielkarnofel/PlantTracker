package com.example.planttracker.database;

@TypeConverters()
@Database(entities = {GymLog.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase {
}
