package com.example.planttracker.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.database.typeConverters.LightLevelTypeConverter;
import com.example.planttracker.database.typeConverters.LocalDateTimeTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/// *** Implemented code for userDAO. Currently creates a problem with an ignored file. 12/4/2024
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
        // TODO: implement singleton design patten to get instance of the database
        /// *** Code borrowed from HW4 ***
        if(INSTANCE == null){
            synchronized (AppDatabase.class){
                if (INSTANCE == null){
                    INSTANCE = Room.databaseBuilder(
                                    context.getApplicationContext(),
                                    AppDatabase.class,
                                    DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .addCallback(addDefaultValues)
                            .build();
                }
            }
        }
        return INSTANCE;
        /// *** End code borrowed from HW4 ***
    }

    private static final RoomDatabase.Callback addDefaultValues = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            // TODO: Add a log here
            databaseWriteExecutor.execute(() -> {
                // TODO: Add default data here
                /// *** The start of default data for userDao, code borrowed from HW4. ***
                UserDAO dao = INSTANCE.userDAO();
                dao.deleteAll();
                User admin = new User("admin1", "admin1");
                admin.setAdmin(true);
                dao.insert(admin);
                User testUser1 = new User("testuser1", "testuser1");
                /// *** The end of default data for userDao, code borrowed from HW4. ***

            });
        }
    };

    public abstract AreaDAO areaDAO();
    public abstract PlantDAO plantDAO();
    public abstract UserDAO userDAO();
}
