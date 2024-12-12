package com.example.planttracker.viewHolders;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.planttracker.ViewAreaActivity;
import com.example.planttracker.database.entities.Area;
import com.example.planttracker.utilities.IntentFactory;

public class AreaAdapter extends ListAdapter<Area, AreaViewHolder> {
    public AreaAdapter(@NonNull DiffUtil.ItemCallback<Area> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return AreaViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaViewHolder holder, int position) {
        Area current = getItem(position);
        holder.bind(current);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.viewAreaActivityIntentFactory(holder.itemView.getContext(), current.getAreaID());
                holder.itemView.getContext().startActivity(intent);
            }
        });
    }

    public static class AreaDiff extends DiffUtil.ItemCallback<Area> {
        @Override
        public boolean areItemsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Area oldItem, @NonNull Area newItem) {
            return oldItem.equals(newItem);
        }
    }
}
