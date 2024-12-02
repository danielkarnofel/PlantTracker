package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String name;
    private String password;
    private boolean isAdmin;

    // TODO: Constructor

    // TODO: toString, equals, hashCode

    // TODO: Getters and Setters
}
