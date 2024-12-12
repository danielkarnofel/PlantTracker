package com.example.planttracker.viewHolders;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.planttracker.ViewAreaActivity;
import com.example.planttracker.ViewPlantActivity;
import com.example.planttracker.database.entities.Plant;
import com.example.planttracker.utilities.IntentFactory;

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.viewPlantActivityIntentFactory(holder.itemView.getContext(), current.getPlantID());
                holder.itemView.getContext().startActivity(intent);
            }
        });
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
