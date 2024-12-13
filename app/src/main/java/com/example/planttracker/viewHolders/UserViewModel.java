package com.example.planttracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public UserViewModel(Application application) {
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<User>> getAllUsers() {
        return repository.getAllUsers();
    }

    public void insert(User user) {
        repository.insertUser(user);
    }

    public void delete(User user) {
        repository.deleteUser(user);
    }
}
