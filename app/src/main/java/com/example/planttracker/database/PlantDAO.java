package com.example.planttracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planttracker.database.entities.Plant;

import java.util.List;

@Dao
public interface PlantDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Plant... plant);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("DELETE FROM " + AppDatabase.PLANT_TABLE)
    void deleteAll();

    // TODO: Should this be ascending or descending?
    // TODO: Refactor this method to take a sorting parameter? Or make multiple query methods and control sorting elsewhere?
    @Query("SELECT * FROM " + AppDatabase.PLANT_TABLE + " WHERE userID = :userID ORDER BY lastWatered DESC")
    LiveData<List<Plant>> getAllPlantsByUserID(int userID);

    @Query("SELECT * FROM " + AppDatabase.PLANT_TABLE + " WHERE plantID = :selectedPlantID")
    LiveData<Plant> getPlantByID(int selectedPlantID);
}
