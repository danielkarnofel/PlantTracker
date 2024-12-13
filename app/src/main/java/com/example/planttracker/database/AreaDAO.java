package com.example.planttracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.planttracker.database.entities.Area;

import java.util.List;

@Dao
public interface AreaDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Area... area);

    @Update
    void update(Area area);

    @Delete
    void delete(Area area);

    @Query("DELETE FROM " + AppDatabase.AREA_TABLE)
    void deleteAll();

    // TODO: How should these be sorted?
    @Query("SELECT * FROM " + AppDatabase.AREA_TABLE + " WHERE userID = :userID")
    LiveData<List<Area>> getAllAreasByUserID(int userID);

    @Query("SELECT * FROM " + AppDatabase.AREA_TABLE + " WHERE areaID = :selectedAreaID")
    LiveData<Area> getAreaByAreaID(int selectedAreaID);
}
