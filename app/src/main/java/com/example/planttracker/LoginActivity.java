package com.example.planttracker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;

import com.example.planttracker.database.AppRepository;
import com.example.planttracker.database.entities.User;
import com.example.planttracker.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.loginActivityLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verifyUser();
            }
        });

        binding.loginActivityCreateAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(SignUpActivity.signupActivityIntentFactory(getApplicationContext()));
            }
        });
    }

    private void verifyUser() {
        String username = binding.loginActivityUsernameEditText.getText().toString();
        String password = binding.loginActivityPasswordEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }
        if (password.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user == null) {
                toastMaker("Invalid username");
                binding.loginActivityUsernameEditText.setSelection(0);
                return;
            }
            if (!password.equals(user.getPassword())) {
                toastMaker("Invalid password");
                binding.loginActivityPasswordEditText.setSelection(0);
                return;
            }
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserID()));
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, LoginActivity.class);
    }
}