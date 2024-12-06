package com.example.planttracker.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.planttracker.database.entities.Area;

@Dao
public interface AreaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Area... area);

    @Delete
    void delete(Area area);

    @Query("DELETE FROM " + AppDatabase.AREA_TABLE)
    void deleteAll();
}
