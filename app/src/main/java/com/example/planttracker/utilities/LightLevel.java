package com.example.planttracker.utilities;

import androidx.annotation.NonNull;

public enum LightLevel {

    VERY_LOW (1, "Very Low"),
    LOW (2, "Low"),
    MEDIUM (3, "Medium"),
    HIGH (4, "High"),
    VERY_HIGH (5, "Very High");

    private final int level;
    private final String representation;

    private LightLevel(int level, String representation) {
        this.level = level;
        this.representation = representation;
    }

    public int getLevelAsInt() {
        return this.level;
    }

    // TODO: is there a more efficient way to do this?
    public static LightLevel getLevelFromInt(int n) {
        for (LightLevel level : LightLevel.values()) {
            if (level.getLevelAsInt() == n) {
                return level;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.representation;
    }
}
