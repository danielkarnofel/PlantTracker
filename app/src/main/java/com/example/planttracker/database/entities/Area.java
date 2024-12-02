package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.planttracker.utilities.LightLevel;

@Entity
public class Area {
    @PrimaryKey(autoGenerate = true)
    private int areaID;
    private int userID;
    private String name;
    private int plantCount;
    private LightLevel lightLevel;

    // TODO: Constructor

    // TODO: toString, equals, hashCode

    // TODO: Getters and Setters
}
