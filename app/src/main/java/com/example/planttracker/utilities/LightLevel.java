package com.example.planttracker.utilities;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.example.planttracker.R;

public enum LightLevel {

    VERY_LOW (1, "Very Low", R.color.light_level_very_low),
    LOW (2, "Low", R.color.light_level_low),
    MEDIUM (3, "Medium", R.color.light_level_medium),
    HIGH (4, "High", R.color.light_level_high),
    VERY_HIGH (5, "Very High", R.color.light_level_very_high);

    private final int level;
    private final String representation;
    private final int color;

    private LightLevel(int level, String representation, int color) {
        this.level = level;
        this.representation = representation;
        this.color = color;
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

    public int getLightLevelColor() {
        return this.color;
    }

    public static LightLevel getSelectionFromRadioGroup(View view, RadioGroup radioGroup) {
        int checkedId = radioGroup.getCheckedRadioButtonId();
        if (checkedId == -1) {
            return null;
        }
        RadioButton checkedRadioButton = view.findViewById(checkedId);
        String checkedRadioButtonTag =  (String) checkedRadioButton.getTag();
        return LightLevel.valueOf(checkedRadioButtonTag);
    }

    @Override
    public String toString() {
        return this.representation;
    }
}
