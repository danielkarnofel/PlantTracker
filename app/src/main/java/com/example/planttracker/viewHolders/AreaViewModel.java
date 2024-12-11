package com.example.planttracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;

import java.util.List;

public class AreaViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public AreaViewModel(Application application) {
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<Area>> getAllAreasByUserID(int userID) {
        return repository.getAllAreasByUserID(userID);
    }

    public void insert(Area area) {
        repository.insertArea(area);
    }
}
