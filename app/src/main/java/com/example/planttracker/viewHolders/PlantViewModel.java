package com.example.planttracker.viewHolders;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.Plant;

import java.util.List;

public class PlantViewModel extends AndroidViewModel {
    private final AppRepository repository;

    public PlantViewModel(Application application) {
        super(application);
        repository = AppRepository.getRepository(application);
    }

    public LiveData<List<Plant>> getAllPlantsByUserID(int userID) {
        return repository.getAllPlantsByUserID(userID);
    }

    public void insert(Plant plant) {
        repository.insertPlant(plant);
    }
}
