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
import com.example.planttracker.databinding.ActivitySignUpBinding;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private AppRepository repository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        repository = AppRepository.getRepository(getApplication());

        binding.signUpActivitySignUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // get the username entered in the signup
                String mUsername = binding.signUpActivityUsernameEditText.getText().toString();

                if (userAlreadyExists(mUsername)) {
                   toastMaker("Username already exists.");
                } else {
                    //TODO: create a user account.
                    toastMaker("Pending implementation");
                }
            }
        });

        binding.signUpActivityCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });
    }

    private boolean userAlreadyExists(String username) {
        LiveData<User> userObserver = repository.getUserByUserName(username);
        boolean userExists = false;

        userObserver.observe(this, user -> {
            //if (user != null ) {
            //TODO: figure out how to pass the information to top method.
            //userExists = true;
            //}
        });

        return userExists;
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent signupActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, SignUpActivity.class);
    }
}