package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.planttracker.utilities.LightLevel;

import java.time.LocalDateTime;

@Entity
public class Plant {
    @PrimaryKey(autoGenerate = true)
    private int plantID;
    private int userID;
    private int areaID;
    private String name;
    private String type;
    private LightLevel lightLevelNeeded;
    private int wateringFrequency;
    private LocalDateTime lastWatered;

    // TODO: Constructor

    // TODO: toString, equals, hashCode

    // TODO: Getters and Setters
}
