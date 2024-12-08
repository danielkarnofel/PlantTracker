package com.example.planttracker.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.example.planttracker.database.entities.User;

import java.util.List;

@Dao
public interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User... user); // Will take zero or more User user arguments.

    @Delete
    void delete(User user);

    // Get all users, ordered by username.
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " ORDER BY username")
    LiveData<List<User>> getAllUsers();

    // Will delete all users.
    @Query("DELETE FROM " + AppDatabase.USER_TABLE)
    void deleteAll();

    // Will select a user by username.
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE username == :username")
    LiveData<User> getUserByUsername(String username);

    // Will select a user by userID.
    @Query("SELECT * FROM " + AppDatabase.USER_TABLE + " WHERE userID == :userID")
    LiveData<User> getUserByUserID(int userID);


}
