package com.example.planttracker.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.R;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.database.entities.Plant;

import java.util.Locale;

public class AreaViewHolder extends RecyclerView.ViewHolder {
    private final TextView nameTextView;
    private final TextView plantCountTextView;

    private AreaViewHolder(View areaView) {
        super(areaView);
        nameTextView = areaView.findViewById(R.id.areaRecyclerItemNameTextView);
        plantCountTextView = areaView.findViewById(R.id.areaRecyclerItemPlantCountTextView);
    }

    public void bind(Area area) {
        nameTextView.setText(area.getName());
        String plantCountString = String.format("%s %s", area.getPlantCount(), area.getPlantCount() == 1 ? "plant" : "plants");
        plantCountTextView.setText(plantCountString);
    }

    static AreaViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.area_recycler_item, parent, false);
        return new AreaViewHolder(view);
    }
}
