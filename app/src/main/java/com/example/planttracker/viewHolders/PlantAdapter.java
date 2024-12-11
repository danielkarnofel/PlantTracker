package com.example.planttracker.viewHolders;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.planttracker.database.entities.Plant;

public class PlantAdapter extends ListAdapter<Plant, PlantViewHolder> {
    public PlantAdapter(@NonNull DiffUtil.ItemCallback<Plant> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public PlantViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return PlantViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull PlantViewHolder holder, int position) {
        Plant current = getItem(position);
        holder.bind(current);
    }

    public static class PlantDiff extends DiffUtil.ItemCallback<Plant> {
        @Override
        public boolean areItemsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Plant oldItem, @NonNull Plant newItem) {
            return oldItem.equals(newItem);
        }
    }
}
