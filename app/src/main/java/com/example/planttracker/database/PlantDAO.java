package com.example.planttracker.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.planttracker.database.entities.Plant;

@Dao
public interface PlantDAO {

    @Insert
    void insert(Plant... plant);

    @Delete
    void delete(Plant plant);

    @Query("DELETE FROM " + AppDatabase.PLANT_TABLE)
    void deleteAll();

    @Query("SELECT * FROM " + AppDatabase.PLANT_TABLE)
    void getAllPlants();
}
