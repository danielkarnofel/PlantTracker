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
import com.example.planttracker.utilities.ToastMaker;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Hide action bar
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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
            ToastMaker.makeToast(this, "Username cannot be empty.");
            return;
        }
        if (password.isEmpty()) {
            ToastMaker.makeToast(this, "Password cannot be empty.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);
        userObserver.observe(this, user -> {
            if (user == null) {
                ToastMaker.makeToast(this, "Invalid username");
                binding.loginActivityUsernameEditText.setSelection(0);
                return;
            }
            if (!password.equals(user.getPassword())) {
                ToastMaker.makeToast(this, "Invalid password");
                binding.loginActivityPasswordEditText.setSelection(0);
                return;
            }
            startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext(), user.getUserID()));
        });
    }

    public static Intent loginActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, LoginActivity.class);
    }
}