package com.example.planttracker.database.entities;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.planttracker.database.AppDatabase;

import java.util.Objects;

@Entity(tableName = AppDatabase.USER_TABLE) //The name of this table.
public class User {
    @PrimaryKey(autoGenerate = true)
    private int userID;
    private String username;
    private String password;
    private boolean isAdmin;

    public User (String username, String password){
        this.username = username;
        this.password = password;
        this.isAdmin = false;
    }

    @Ignore
    public User (String username, String password, boolean isAdmin){
        this(username, password);
        this.isAdmin = isAdmin;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userID == user.userID && isAdmin == user.isAdmin && Objects.equals(username, user.username) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, username, password, isAdmin);
    }

    @Override
    public String toString() {
        return "User{" +
                "userID=" + userID +
                ", userName='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isAdmin=" + isAdmin +
                '}';
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
