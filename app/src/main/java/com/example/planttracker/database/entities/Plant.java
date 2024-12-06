package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.example.planttracker.database.AppDatabase;
import com.example.planttracker.utilities.LightLevel;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity(tableName = AppDatabase.PLANT_TABLE)
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

    public Plant(int userID, int areaID, String name, String type, LightLevel lightLevelNeeded, int wateringFrequency, LocalDateTime lastWatered) {
        this.userID = userID;
        this.areaID = areaID;
        this.name = name;
        this.type = type;
        this.lightLevelNeeded = lightLevelNeeded;
        this.wateringFrequency = wateringFrequency;
        this.lastWatered = lastWatered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Plant plant = (Plant) o;
        return plantID == plant.plantID && userID == plant.userID && areaID == plant.areaID && wateringFrequency == plant.wateringFrequency && Objects.equals(name, plant.name) && Objects.equals(type, plant.type) && lightLevelNeeded == plant.lightLevelNeeded && Objects.equals(lastWatered, plant.lastWatered);
    }

    @Override
    public int hashCode() {
        return Objects.hash(plantID, userID, areaID, name, type, lightLevelNeeded, wateringFrequency, lastWatered);
    }

    @Override
    public String toString() {
        return "Plant{" +
                "plantID=" + plantID +
                ", userID=" + userID +
                ", areaID=" + areaID +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", lightLevelNeeded=" + lightLevelNeeded +
                ", wateringFrequency=" + wateringFrequency +
                ", lastWatered=" + lastWatered +
                '}';
    }

    public int getPlantID() {
        return plantID;
    }

    public void setPlantID(int plantID) {
        this.plantID = plantID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LightLevel getLightLevelNeeded() {
        return lightLevelNeeded;
    }

    public void setLightLevelNeeded(LightLevel lightLevelNeeded) {
        this.lightLevelNeeded = lightLevelNeeded;
    }

    public int getWateringFrequency() {
        return wateringFrequency;
    }

    public void setWateringFrequency(int wateringFrequency) {
        this.wateringFrequency = wateringFrequency;
    }

    public LocalDateTime getLastWatered() {
        return lastWatered;
    }

    public void setLastWatered(LocalDateTime lastWatered) {
        this.lastWatered = lastWatered;
    }
}
