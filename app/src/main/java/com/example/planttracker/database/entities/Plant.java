package com.example.planttracker.database.entities;

import com.example.planttracker.utilities.LightLevel;

import java.time.LocalDateTime;

public class Plant {
    private String name;
    private String type;
    private LightLevel lightLevelNeeded;
    private int wateringFrequency;
    private LocalDateTime lastWatered;
}
