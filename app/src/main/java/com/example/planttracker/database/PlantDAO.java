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

    @Update
    void update(Plant plant);

    @Delete
    void delete(Plant plant);

    @Query("DELETE FROM " + AppDatabase.PLANT_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.PLANT_TABLE + " WHERE userID = :userID ORDER BY lastWatered ASC")
    LiveData<List<Plant>> getAllPlantsByUserID(int userID);

    @Query("SELECT * FROM " + AppDatabase.PLANT_TABLE + " WHERE plantID = :selectedPlantID")
    LiveData<Plant> getPlantByID(int selectedPlantID);

}
