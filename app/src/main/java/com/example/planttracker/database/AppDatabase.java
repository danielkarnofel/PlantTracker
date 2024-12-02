package com.example.planttracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@TypeConverters()
@Database(entities = {Plant.class, Area.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase {
    public static final String AREA_TABLE = "areaTable";
    public static final String PLANT_TABLE = "plantTable";
    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "appDatabase";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        // TODO: implement singleton design patten to get instance of the database
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // TODO: Add a log here
            databaseWriteExecutor.execute(() -> {
                // TODO: Add default data here
            });
        }
    };

    public abstract AppDAO appDAO();
}
