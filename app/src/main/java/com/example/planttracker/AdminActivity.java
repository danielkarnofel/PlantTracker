package com.example.planttracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.planttracker.databinding.ActivityAdminBinding;
import com.example.planttracker.utilities.IntentFactory;
import com.example.planttracker.viewHolders.UserAdapter;
import com.example.planttracker.viewHolders.UserViewModel;

public class AdminActivity extends BaseActivity {
    private ActivityAdminBinding binding;
    private UserViewModel userViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAdminBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Set up User recycler view
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);
        RecyclerView recyclerView = binding.adminActivityRecyclerView;
        final UserAdapter adapter = new UserAdapter(new UserAdapter.UserDiff(), userViewModel);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        userViewModel.getAllUsers().observe(this, adapter::submitList);

        binding.adminActivityHomeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = IntentFactory.mainActivityIntentFactory(getApplicationContext(), loggedInUserID);
                startActivity(intent);
            }
        });
    }


}