package com.example.planttracker.database.typeConverters;

import androidx.room.TypeConverter;

import com.example.planttracker.utilities.LightLevel;

public class LightLevelTypeConverter {

    @TypeConverter
    public int convertLightLevelToInt(LightLevel level) {
        return level.getLevelAsInt();
    }

    @TypeConverter
    public LightLevel convertIntToLightLevel(int n) {
        return LightLevel.getLevelFromInt(n);
    }
}
