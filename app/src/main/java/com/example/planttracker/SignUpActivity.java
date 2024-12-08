package com.example.planttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                String mPassword1 = binding.signUpActivityPasswordEditText.getText().toString();
                String mPassword2 = binding.signUpActivityConfirmEditText.getText().toString();

                if (signupInformationComplete(mUsername, mPassword1, mPassword2)) {
                    if (repository.checkUsernameExists(mUsername)) {
                        toastMaker("Username already exists.");
                    } else {
                        repository.insertUser(new User(mUsername, mPassword1, false));
                        showSuccessDialog();
                    }
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

    private boolean signupInformationComplete(String username, String password1, String password2) {
        if (username.isEmpty()) {
            toastMaker("Missing username!");
            binding.signUpActivityUsernameEditText.setSelection(0);
            return false;
        }

        if (password1.isEmpty()) {
            toastMaker("Missing password!");
            binding.signUpActivityPasswordEditText.setSelection(0);
            return false;
        }

        if (password2.isEmpty()) {
            toastMaker("Missing password confirmation!");
            binding.signUpActivityConfirmEditText.setSelection(0);
            return false;
        }

        if (!password1.equals(password2)) {
            toastMaker("Passwords does not match!");
            binding.signUpActivityPasswordEditText.setSelection(0);
            return false;
        }

        return true;
    }

    private void showSuccessDialog() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(SignUpActivity.this);

        alertBuilder.setTitle("Success");
        alertBuilder.setMessage("Your account was successfully created.");
        alertBuilder.setCancelable(false);
        alertBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = LoginActivity.loginActivityIntentFactory(getApplicationContext());
                startActivity(intent);
            }
        });

        alertBuilder.create().show();
    }

    private void toastMaker(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public static Intent signupActivityIntentFactory(Context applicationContext) {
        return new Intent(applicationContext, SignUpActivity.class);
    }
}