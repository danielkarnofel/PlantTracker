package com.example.planttracker.database;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.planttracker.MainActivity;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.database.typeConverters.LightLevelTypeConverter;
import com.example.planttracker.database.typeConverters.LocalDateTimeTypeConverter;
import com.example.planttracker.utilities.LightLevel;

import java.time.LocalDateTime;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@TypeConverters({LightLevelTypeConverter.class, LocalDateTimeTypeConverter.class})
@Database(entities = {Plant.class, Area.class, User.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public static final String AREA_TABLE = "areaTable";
    public static final String PLANT_TABLE = "plantTable";
    public static final String USER_TABLE = "userTable";
    private static final String DATABASE_NAME = "appDatabase";
    private static volatile AppDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            Log.i(MainActivity.LOG_TAG, "Database created.");

            // Create default users:
            User testAdmin1 = new User("admin1", "admin1", true);
            User testUser1 = new User("testUser1", "testUser1");
            User testUser2 = new User("testUser2", "testUser2");

            // TODO: Remove this once Add New Plant function is working
            // Create default plants (for testing only)
            Plant testPlant1 = new Plant(1, 1, "Mr. Leaves", "Fern", LightLevel.LOW, 3, LocalDateTime.now());
            Plant testPlant2 = new Plant(1, 2, "Leaf Erikson", "Shrub", LightLevel.MEDIUM, 7, LocalDateTime.now());
            Plant testPlant3 = new Plant(1, 3, "Cooler Cactus", "Succulent", LightLevel.HIGH, 14, LocalDateTime.now());

            // TODO: Remove this once Add New Area function is working
            // Create default areas (for testing only)
            Area testArea1 = new Area(1, "Living Room", 1, LightLevel.MEDIUM);
            Area testArea2 = new Area(1, "Bedroom", 1, LightLevel.LOW);
            Area testArea3 = new Area(1, "Balcony", 1, LightLevel.HIGH);

            // Clear the user table and insert all default users:
            databaseWriteExecutor.execute(() -> {
                UserDAO userDAO = INSTANCE.userDAO();
                userDAO.deleteAll();
                userDAO.insert(testAdmin1, testUser1, testUser2);

                PlantDAO plantDAO = INSTANCE.plantDAO();
                plantDAO.deleteAll();
                plantDAO.insert(testPlant1, testPlant2, testPlant3);

                AreaDAO areaDAO = INSTANCE.areaDAO();
                areaDAO.deleteAll();
                areaDAO.insert(testArea1, testArea2, testArea3);
            });
        }
    };

    public abstract AreaDAO areaDAO();
    public abstract PlantDAO plantDAO();
    public abstract UserDAO userDAO();
}
