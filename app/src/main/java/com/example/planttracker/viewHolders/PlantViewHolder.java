package com.example.planttracker.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.R;
import com.example.planttracker.database.entities.Plant;

import java.util.Locale;

public class PlantViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView typeTextView;
    private final TextView lastWateredTextView;

    private PlantViewHolder(View plantView) {
        super(plantView);
        nameTextView = plantView.findViewById(R.id.plantRecyclerItemNameTextView);
        typeTextView = plantView.findViewById(R.id.plantRecyclerItemTypeTextView);
        lastWateredTextView = plantView.findViewById(R.id.plantRecyclerItemLastWateredTextView);
    }

    public void bind(Plant plant) {
        nameTextView.setText(plant.getName());
        typeTextView.setText(plant.getType());
        lastWateredTextView.setText(String.format(Locale.getDefault(), "Last watered %s days ago", plant.getLastWatered()));
    }

    static PlantViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.plant_recycler_item, parent, false);
        return new PlantViewHolder(view);
    }
}
