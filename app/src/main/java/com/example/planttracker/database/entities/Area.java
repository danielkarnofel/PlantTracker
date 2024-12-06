package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import com.example.planttracker.database.AppDatabase;
import com.example.planttracker.utilities.LightLevel;

import java.util.Objects;

@Entity(tableName = AppDatabase.AREA_TABLE)
public class Area {
    @PrimaryKey(autoGenerate = true)
    private int areaID;
    private int userID;
    private String name;
    private int plantCount;
    private LightLevel lightLevel;

    public Area(int userID, String name, int plantCount, LightLevel lightLevel) {
        this.userID = userID;
        this.name = name;
        this.plantCount = plantCount;
        this.lightLevel = lightLevel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Area area = (Area) o;
        return areaID == area.areaID && userID == area.userID && plantCount == area.plantCount && Objects.equals(name, area.name) && lightLevel == area.lightLevel;
    }

    @Override
    public int hashCode() {
        return Objects.hash(areaID, userID, name, plantCount, lightLevel);
    }

    @Override
    public String toString() {
        return "Area{" +
                "areaID=" + areaID +
                ", userID=" + userID +
                ", name='" + name + '\'' +
                ", plantCount=" + plantCount +
                ", lightLevel=" + lightLevel +
                '}';
    }

    public int getAreaID() {
        return areaID;
    }

    public void setAreaID(int areaID) {
        this.areaID = areaID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlantCount() {
        return plantCount;
    }

    public void setPlantCount(int plantCount) {
        this.plantCount = plantCount;
    }

    public LightLevel getLightLevel() {
        return lightLevel;
    }

    public void setLightLevel(LightLevel lightLevel) {
        this.lightLevel = lightLevel;
    }
}
