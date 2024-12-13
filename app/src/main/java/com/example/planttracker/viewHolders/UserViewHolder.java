package com.example.planttracker.viewHolders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.R;
import com.example.planttracker.database.entities.User;

public class UserViewHolder extends RecyclerView.ViewHolder {

    private final TextView usernameTextView;
    private final TextView isAdminTextView;
    private final Button setAdminButton;
    private final Button deleteButton;

    private UserViewModel userViewModel;

    private UserViewHolder(View userView, UserViewModel userViewModel) {
        super(userView);
        usernameTextView = userView.findViewById(R.id.userRecyclerItemUsernameTextView);
        isAdminTextView = userView.findViewById(R.id.userRecyclerItemIsAdminTextView);
        setAdminButton = userView.findViewById(R.id.userRecyclerItemSetAdminButton);
        deleteButton = userView.findViewById(R.id.userRecyclerItemDeleteButton);
        this.userViewModel = userViewModel;
    }

    public void bind(User user) {
        usernameTextView.setText(user.getUsername());
        isAdminTextView.setText(user.isAdmin() ? "YES" : "NO");

        setAdminButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user.setAdmin(!user.isAdmin());
                userViewModel.insert(user);
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userViewModel.delete(user);
            }
        });
    }

    static UserViewHolder create(ViewGroup parent, UserViewModel userViewModel) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_recycler_item, parent, false);
        return new UserViewHolder(view, userViewModel);
    }
}
