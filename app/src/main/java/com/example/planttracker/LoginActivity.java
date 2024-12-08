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
                Intent intent = SignUpActivity.signupActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private void verifyUser() {
        String username = binding.loginActivityUsernameEditText.getText().toString();

        if (username.isEmpty()) {
            toastMaker("Username cannot be empty.");
            return;
        }

        LiveData<User> userObserver = repository.getUserByUsername(username);

        userObserver.observe(this, user -> {
            if (user != null) {
                String password = binding.loginActivityPasswordEditText.getText().toString();

                if (password.equals(user.getPassword())) {
                    //TODO: may need to update this to pass the user information to main activity
                    startActivity(MainActivity.mainActivityIntentFactory(getApplicationContext()));
                } else {
                    toastMaker("Invalid password");
                    binding.loginActivityPasswordEditText.setSelection(0);
                }
            } else {
                toastMaker(String.format("No user %s is not a valid username", username));
                binding.loginActivityUsernameEditText.setSelection(0);
            }
        });
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent loginActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, LoginActivity.class);
    }
}