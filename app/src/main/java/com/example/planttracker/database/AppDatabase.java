package com.example.planttracker.database;

import androidx.room.Database;
import androidx.room.TypeConverters;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;

@TypeConverters()
@Database(entities = {Plant.class, Area.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase {
}
